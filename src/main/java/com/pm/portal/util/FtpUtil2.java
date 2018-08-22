package com.pm.portal.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@SuppressWarnings("all")
public class FtpUtil2 {
	private static final Log logger = LogFactory.getLog(FtpUtil2.class);
	private static FtpUtil2 ftpUtils;
	private FTPClient ftpClient;

	private String serverUrl; // 服务器地址
	private String port; // 服务器端口
	private String username; // 用户登录名
	private String password; // 用户登录密码
	private InputStream is; // 文件下载输入流

	/**
	 * 私有构造方法
	 */
	private FtpUtil2(String FTP_ADDRESS, String FTP_PORT, String FTP_USERNAME, String FTP_PASSWORD) {
		serverUrl = FTP_ADDRESS; // 设置服务器地址
		port = FTP_PORT; // 设置端口
		username = FTP_USERNAME; // 设置用户名
		password = FTP_PASSWORD; // 设置密码
		if (null == ftpClient) {
			System.out.println("new");
			ftpClient = new FTPClient();

		}
	}

	/**
	 * 获取FTPUtils对象实例
	 * 
	 * @return FTPUtils对象实例
	 */
	public synchronized static FtpUtil2 getInstance(String FTP_ADDRESS, String FTP_PORT, String FTP_USERNAME,
			String FTP_PASSWORD) {
	
		if (null == ftpUtils || !ftpUtils.getServerUrl().equals(FTP_ADDRESS)) {
			System.out.println("1");
			ftpUtils = new FtpUtil2(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		}
		return ftpUtils;
	}

	/**
	 * 初始化FTP服务器连接属性
	 */

	/**
	 * 连接（配置通用连接属性）至服务器
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param remotePath
	 *            当前访问目录
	 * @return <b>true</b>：连接成功 <br/>
	 *         <b>false</b>：连接失败
	 */
	public boolean connectToTheServer(String remotePath) {
		// 定义返回值
		boolean result = false;
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftpClient.connect(serverUrl, Integer.parseInt(port));
			ftpClient.setConnectTimeout(150000);
			// 登录服务器
			ftpClient.login(username, password);
			System.out.println("login");
			System.out.println(ftpClient.getReplyCode());
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				
				// 不合法时断开连接
				ftpClient.disconnect();
				// 结束程序
				return result;
			}
			System.out.println("login2");
			// 设置文件传输模式
			// 被动模式
			// ftpClient.enterLocalPassiveMode();
			// 创建目录
			
			
			if (!ftpClient.changeWorkingDirectory(remotePath)) {
				//如果目录不存在创建目录
				System.out.println("remotePath,"+remotePath);
				String basePath="/home/ftpuser/www/files";
	
				String[] dirs = remotePath.replace(basePath, "").split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftpClient.changeWorkingDirectory(tempPath)) {
						if (!ftpClient.makeDirectory(tempPath)) {
							System.out.println("无法创建目录,"+tempPath);
							return result;
						} else {
							System.out.println("创建目录,"+tempPath);
							ftpClient.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			
			
			
			
			// 设置文件操作目录
			result = ftpClient.changeWorkingDirectory(remotePath);
			// 设置文件类型，二进制
			result = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftpClient.setBufferSize(3072);
			// 设置字符编码
			ftpClient.setControlEncoding("UTF-8");

		} catch (IOException e) {
			logger.error("连接FTP服务器异常", e);
			throw new RuntimeException("连接FTP服务器异常", e);
		}
		return result;
	}

	/**
	 * 上传文件至FTP服务器
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param storePath
	 *            上传文件存储路径
	 * @param fileName
	 *            上传文件存储名称
	 * @param is
	 *            上传文件输入流
	 * @return <b>true</b>：上传成功 <br/>
	 *         <b>false</b>：上传失败
	 */
	public boolean storeFile(String storePath, String fileName, InputStream is) {
		boolean result = false;
		try {
			// 连接至服务器
			System.out.println("storePath,"+storePath);
			result = connectToTheServer(storePath);
			System.out.println("连接到服务器,"+result);
			// 判断服务器是否连接成功
			if (result) { 
				// 上传文件
				System.out.println("fileName,"+fileName);
//				File savedFile = new File(storePath, fileName);
//				savedFile.setWritable(true,false);
				System.out.println("文件长度,"+is.available());
				//ftpClient.enterLocalPassiveMode();
				result = ftpClient.storeFile(fileName, is);
				System.out.println("上传文件是否成功,"+result);
			}
			// 关闭输入流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 判断输入流是否存在
			if (null != is) {
				try {
					// 关闭输入流
					is.close();
				} catch (IOException e) {
					logger.error("上传文件至FTP异常" + e.getMessage());
					throw new RuntimeException("上传文件至FTP异常", e);
				}
			}
			// 登出服务器并断开连接
			ftpUtils.logout();
		}
		return result;
	}

	/**
	 * 下载FTP服务器文件至本地<br/>
	 * 操作完成后需调用logout方法与服务器断开连接
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param remotePath
	 *            下载文件存储路径
	 * @param fileName
	 *            下载文件存储名称
	 * @return <b>InputStream</b>：文件输入流
	 * @throws Exception 
	 */
	public InputStream retrieveFile(String remotePath, String fileName) throws Exception {
		try {
			boolean result = false;
			// 连接至服务器
			result = connectToTheServer(remotePath);
			// 判断服务器是否连接成功
			if (result) {
				// 获取文件输入流
				is = ftpClient.retrieveFileStream(fileName);
			}
		} catch (IOException e) {
			logger.error("从FTP下载文件到本地异常" + e.getMessage());
			throw new Exception("从FTP下载文件到本地异常", e);
		}
		return is;
	}
	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * 
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 * @throws Exception 
	 */
	public boolean retrieveFile(String remotePath, String fileName, String localPath) throws Exception {
		// 初始表示下载失败
		boolean success = false;
		// 表示是否连接成功
		boolean result = false;
		File file = new File(localPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			// 连接至服务器
			result = connectToTheServer(remotePath);
			if (result) {
				// 列出该目录下所有文件
				FTPFile[] fs = ftpClient.listFiles();
				// 遍历所有文件，找到指定的文件
				for (FTPFile ff : fs) {
					if (ff.getName().equals(fileName)) {
						// 根据绝对路径初始化文件
						File localFile = new File(localPath + "/" + ff.getName());
						// 输出流
						OutputStream is = new FileOutputStream(localFile);
						// 下载文件
						success = ftpClient.retrieveFile(ff.getName(), is);
						is.close();
					}
				}
			}

		} catch (IOException e) {
			logger.error("从FTP服务器下载文件异常", e);
			throw new Exception(e.toString());
		} finally {
			// 登出服务器并断开连接
			ftpUtils.logout();
		}
		return success;
	}

	/**
	 * 删除FTP服务器文件
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param remotePath
	 *            当前访问目录
	 * @param fileName
	 *            文件存储名称
	 * @return <b>true</b>：删除成功 <br/>
	 *         <b>false</b>：删除失败
	 */
	public boolean deleteFile(String remotePath, String fileName) {
		boolean result = false;
		// 连接至服务器
		result = connectToTheServer(remotePath);
		// 判断服务器是否连接成功
		if (result) {
			try {
				// 删除文件
				result = ftpClient.deleteFile(fileName);
			} catch (IOException e) {
				logger.error("删除FTP服务器上的 文件异常" + e.getMessage());
				throw new RuntimeException("删除FTP服务器上的 文件异常", e);
			} finally {
				// 登出服务器并断开连接
				ftpUtils.logout();
			}
		}
		return result;
	}

	public boolean deleteDir(String remotePath, String fileName) {
		boolean result = false;
		// 连接至服务器
		result = connectToTheServer(remotePath);
		// 判断服务器是否连接成功
		if (result) {
			try {
				// 删除文件
				ftpClient.rmd(fileName);
			} catch (IOException e) {
				logger.error("删除FTP服务器上的 文件异常" + e.getMessage());
				throw new RuntimeException("删除FTP服务器上的 文件异常", e);
			} finally {
				// 登出服务器并断开连接
				ftpUtils.logout();
			}
		}
		return result;
	}

	/**
	 * 检测FTP服务器文件是否存在
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param remotePath
	 *            检测文件存储路径
	 * @param fileName
	 *            检测文件存储名称
	 * @return <b>true</b>：文件存在 <br/>
	 *         <b>false</b>：文件不存在
	 */
	public boolean checkFile(String remotePath, String fileName) {
		boolean result = false;
		try {
			// 连接至服务器
			result = connectToTheServer(remotePath);
			// 判断服务器是否连接成功
			if (result) {
				// 默认文件不存在
				result = false;
				// 获取文件操作目录下所有文件名称
				String[] remoteNames = ftpClient.listNames();
				// 循环比对文件名称，判断是否含有当前要下载的文件名
				for (String remoteName : remoteNames) {
					if (fileName.equals(remoteName)) {
						result = true;
					}
				}
			}
		} catch (IOException e) {
			logger.error("检查FTP文件是否存在异常" + e.getMessage());
			throw new RuntimeException("检查FTP文件是否存在异常", e);
		} finally {
			// 登出服务器并断开连接
			ftpUtils.logout();
		}
		return result;
	}

	/**
	 * 登出服务器并断开连接
	 * 
	 * @param ftp
	 *            FTPClient对象实例
	 * @return <b>true</b>：操作成功 <br/>
	 *         <b>false</b>：操作失败
	 */
	public boolean logout() {
		boolean result = false;
		if (null != is) {
			try {
				// 关闭输入流
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("登录FTP服务器异常" + e.getMessage());
				throw new RuntimeException("登录FTP服务器异常", e);
			}
		}
		if (null != ftpClient) {
			try {
				// 登出服务器
				result = ftpClient.logout();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("登录FTP服务器异常" + e.getMessage());
				throw new RuntimeException("登录FTP服务器异常", e);
			} finally {
				// 判断连接是否存在
				if (ftpClient.isConnected()) {
					try {
						// 断开连接
						ftpClient.disconnect();
					} catch (IOException e) {
						logger.error("关闭FTP服务器异常" + e.getMessage());
						throw new RuntimeException("关闭FTP服务器异常", e);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 * @throws Exception 
	 */
	public static boolean downFile(String url, int port, String username, String password, String remotePath,
			String fileName, String localPath) throws Exception {
		// 初始表示下载失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			reply = ftp.getReplyCode();
			/*
			 * 判断是否连接成功
			 */
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			} else {
				// 登录ftp
				if (ftp.login(username, password)) {
					// 转到指定下载目录
					ftp.changeWorkingDirectory(remotePath);
					// 列出该目录下所有文件
					FTPFile[] fs = ftp.listFiles();
					// 遍历所有文件，找到指定的文件
					for (FTPFile ff : fs) {
						if (ff.getName().equals(fileName)) {
							// 根据绝对路径初始化文件
							File localFile = new File(localPath + "/" + ff.getName());
							// 输出流
							OutputStream is = new FileOutputStream(localFile);
							// 下载文件
							ftp.retrieveFile(ff.getName(), is);
							is.close();
						}
					}
					// 退出ftp
					ftp.logout();
					// 下载成功
					success = true;
				}
			}
		} catch (IOException e) {
			logger.error("从FTP服务器下载文件异常" + e.getMessage());
			throw new Exception(e.toString());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error("关闭FTP连接异常" + ioe.getMessage());
					throw new Exception(ioe.toString());
				}
			}
		}
		return success;
	}

	/**
	 * 读取本地TXT
	 * 
	 * @param filepath
	 *            txt文件目录即文件名
	 * @throws Exception 
	 */

	public ArrayList<String> readtxt(String filepath) throws Exception {
		ArrayList<String> readList = new ArrayList<String>();
		ArrayList retList = new ArrayList();
		try {
			String temp = null;
			File f = new File(filepath);
			String adn = "";
			// 指定读取编码用于读取中文
			InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			// bufReader = new BufferedReader(new FileReader(filepath));
			do {
				temp = reader.readLine();
				readList.add(temp);
			} while (temp != null);
			read.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("读取本地txt异常" + e.getMessage());
			throw new Exception(e.toString());
		}
		return readList;
	}



	/**
	 * 把配置文件先写到本地的一个文件中取
	 * 
	 * @param ftpPath
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public void write(String fileName, String fileContext, String writeTempFielPath) throws Exception {
		try {
			logger.info("开始写配置文件");
			File f = new File(writeTempFielPath + "/" + fileName);
			if (!f.exists()) {
				if (!f.createNewFile()) {
					logger.info("文件不存在，创建失败!");
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.write(fileContext.replaceAll("\n", "\r\n"));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			logger.error("写文件没有成功");
			e.printStackTrace();
			throw new Exception(e.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		FtpUtil2 ftp = FtpUtil2.getInstance("192.168.1.245", "21", "ftpuser", "ftpuser");
		//FtpUtil2 ftp = FtpUtil2.getInstance("192.168.100.154", "21", "ftpuser", "ftpuser");
		String localPath = "D:/a.json";
		String remotePath = "/home/ftpuser/www/files/tmp";
		String fileName = "a1.p";
		
//		byte[] ArrayLetters = {  
//			    0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,  
//			    0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A  
//			    };  
//		String tmp = new String(ArrayLetters); 
//		
//		ByteArrayInputStream bais = new ByteArrayInputStream(ArrayLetters);  
//		// ftp.deleteDir(remotePath, fileName);
//		 //文件上传
//		 try {
//		 FileInputStream in=new FileInputStream(new File(localPath));
//		 boolean flag = ftp.storeFile(remotePath, fileName,bais );
//		 System.out.println("文件上传结果："+flag);
//		 } catch (FileNotFoundException e) {
//		 e.printStackTrace();
//		 }
		// 下载
		//InputStream is = ftp.retrieveFile("/home/ftpuser/www/files/tmp/admin/2017/08/25", "1503643008662993_jdk7_32_install_url.sls");
		//boolean storeFile = ftp.storeFile("/home/ftpuser/www/files/base//admin/2017/08/25/V150364298894024", "aa", is);
        //System.out.println(storeFile);
		 //文件下载
//		 InputStream in=ftp.retrieveFile(remotePath, fileName);
//		 StringBuffer resultBuffer = new StringBuffer();
//		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		 String data = null;
//		 try {
//		 while ((data = br.readLine()) != null) {
//		 resultBuffer.append(data + "\n");
//		 }
//		 } catch (IOException e) {
//		 logger.error("文件读取错误。");
//		 e.printStackTrace();
//		 }
		// System.out.println(resultBuffer.toString());

		 //在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
		 try {
		 InputStream input = new
		 ByteArrayInputStream("一切只能靠自己！".getBytes("utf-8"));
		 boolean flag = ftp.storeFile(remotePath,"test.txt",input);
		 System.out.println(flag);
		 } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
		 }

	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	
	private long parseSize(String size) {  
	      Assert.hasLength(size, "Size must not be empty");  
	      size = size.toUpperCase();  
	      return size.endsWith("KB")?Long.valueOf(size.substring(0, size.length() - 2)).longValue() * 1024L:(size.endsWith("MB")?Long.valueOf(size.substring(0, size.length() - 2)).longValue() * 1024L * 1024L:Long.valueOf(size).longValue());  
	  }
}