package org.seckill.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FTPUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
	
	private static FTPClient ftpClient;
	
	private static String REMOTE_PATH = "deploy" + File.separator;
	
	private static int FTP_PORT = 21;
	private static String FTP_USERNAME = "administrator";
	private static String FTP_PASSWORD = "Ewell123";
	

	/**
	 * 获取FTPClient对象
	 * @param url
	 *            FTP服务器IP地址
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
public static FTPClient getFTPClient(String url) {
		
		FTPClient ftpClient = new FTPClient();
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftpClient.connect(url, FTP_PORT);
			// 登录服务器
			boolean isSuccess = ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
			if (isSuccess) {
				logger.info("{}登录成功。", url);
			}
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.warn("未连接到 {}, 用户名或密码错误。", url);
				// 不合法时断开连接
				ftpClient.disconnect();
				// 结束程序
				return new FTPClient();
			}else {
				logger.info("{}连接成功。", url);
			}
			ftpClient.configure(getFtpConfig());
			// 设置文件操作目录
			ftpClient.changeWorkingDirectory(REMOTE_PATH);
			// 设置文件类型，二进制
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftpClient.setBufferSize(3072);
			// 设置字符编码
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.enterLocalPassiveMode();
			

		} catch (SocketException e) {
			logger.error("{}的IP地址可能错误，请正确配置。", url);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("{}的端口错误,请正确配置。", url);
			e.printStackTrace();
		}

		return ftpClient;
	}

	// 设置FTP客服端的配置--一般可以不设置
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}

	/*
	  public static void recursion(String root){ 
	  File file = new File(root);
	  File[] subFile = file.listFiles(); 
	  for (int i = 0; i < subFile.length; i++) { 
	  if (subFile[i].isDirectory()) { 
	 System.out.println("目录: " + subFile[i].getName()); recursion(subFile[i].getAbsolutePath()); 
	  } else{
	  System.out.println("文件: " + subFile[i].getName()); 
	        } 
	     } 
	  }
	 */

	/**
	 * 列出Ftp服务器上的所有文件和目录
	 */
	public static List<String> listRemoteAllFiles(String url) {

		ftpClient = getFTPClient(url);
		List<String> fileList = new ArrayList<String>();

		try {
			FTPFile[] files = ftpClient.listFiles();
			for (int i = 0; i < files.length; i++) {

				if (files[i].isFile()) {                        //文件
					fileList.add(files[i].getName());

				} /*else if (files[i].isDirectory()) {          //目录
					if (files[i].getName().contains("."))
						continue;
					fileList.add(files[i].getName());
				}*/
			}

			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 判断连接是否存在
				if (ftpClient.isConnected()) {
					// 断开连接
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}


	/**
	 * 上传文件至FTP服务器
	 * 
	 * @param url
	 *            服务器IP地址
	 * @param storePath
	 *            服务器文件存储路径
	 * @param fileName
	 *            服务器文件存储名称
	 * @param is
	 *            文件输入流
	 * @return true：   上传成功 
	 *         false：上传失败
	 */
	public static boolean uploadFile(String url,String fileName, InputStream is) {
		boolean result = false;
		
		try {
			ftpClient = getFTPClient(url);
			// 上传文件
			result = ftpClient.storeFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), is);
			// 关闭输入流
			is.close();
			// 登出服务器
			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != is) {
					// 关闭输入流
					is.close();
				}
				// 判断连接是否存在
				if (ftpClient.isConnected()) {
					// 断开连接
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 从FTP服务器下载文件至本地
	 * 
	 * @param url
	 *            服务器IP地址
	 * @param remotePath
	 *            服务器文件存储路径
	 * @param fileName
	 *            服务器文件存储名称
	 * @param localPath
	 *            本地文件存储路径
	 * @return true：   下载成功
	 *         false：下载失败
	 */
	public static boolean downloadFile(HttpServletResponse response, String url,  String fileName) {

		boolean result = false;
        // 创建输入流
		InputStream is = null; 
		// 创建输出流
		OutputStream os = null;
		try {
			ftpClient = getFTPClient(url);
			
			// 获取文件操作目录下所有文件名称
			FTPFile[] files = ftpClient.listFiles();
			// 循环比对文件名称，判断是否含有当前要下载的文件名
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile() && fileName.equals(files[i].getName())) {                        //文件
					result = true;
					break;
				} 
			}
			// 文件名称比对成功时，进入下载流程
			if (result) {
				/*下载到本地
				File filePath = new File(localPath);
				if (!filePath.exists()) {
					filePath.mkdir();
				}
				// 构造本地文件对象
				File localFile = new File(localPath + File.separator + fileName);
				// 构造文件输出流
				OutputStream os = new FileOutputStream(localFile);
				// 下载文件
				result = ftpClient.retrieveFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), os);
				// 关闭输出流
				os.close();*/
				
				// 浏览器下载
				response.reset();  
				response.setContentType("multipart/form-data");  
				// 设置响应头，控制浏览器下载该文件
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				// 读取要下载的文件，保存到文件输入流
				is = ftpClient.retrieveFileStream(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				// 创建输出流
				os = response.getOutputStream();
				// 创建缓冲区
				byte b[] = new byte[1024];
				int len;
				// 循环将输入流中的内容读取到缓冲区当中
				while ((len = is.read(b)) != -1) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					os.write(b, 0, len);
				}
				// 关闭输入流
				is.close();
				// 关闭输出流
				os.close();
				os.flush();
			}else {
				return result;
			}
			// 登出服务器
			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != is) {
					// 关闭入输出流
					is.close();
				}
				// 判断输出流是否存在
				if (null != os) {
					// 关闭输出流
					os.close();
				}
				// 判断连接是否存在
				if (ftpClient.isConnected()) {
					// 断开连接
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}