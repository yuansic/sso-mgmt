package com.x.platform.modules.sys.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.x.platform.modules.sys.utils.FtpUtils;
import org.apache.log4j.Logger;

import com.x.platform.common.utils.DateUtils;
import com.x.platform.modules.sys.utils.FtpUtils;

public class ReadFileThred extends Thread {
	private static final Logger LOG = Logger.getLogger(ReadFileThred.class);
	public BlockingQueue<String[]> userQueue;
	public BlockingQueue<String[]> officeQueue;

	public ReadFileThred(BlockingQueue<String[]> userQueue, BlockingQueue<String[]> officeQueue) {
		this.userQueue = userQueue;
		this.officeQueue = officeQueue;
	}

	public void run() {
		LOG.error("开始获取ftp文件："+DateUtils.getDateTime());
		FtpUtils ftp = new FtpUtils();
		List<String> fileList = ftp.getFileList();
		for (String file : fileList) {
			LOG.error("ftp文件名："+file);
			try {
				if (file.equals("office.txt") || file.equals("user.txt")) {
					readFile(file, ftp);
//					ftp.deleteFile(file);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		LOG.error("获取ftp文件结束："+DateUtils.getDateTime());
		ftp.closeServer();
	}

	public void readFile(String fileName, FtpUtils ftp) throws ParseException {
		InputStream ins = null;
		try {
			// 从服务器上读取指定的文件
			LOG.error("开始读取文件："+fileName);
			ins = ftp.readFile(fileName);
			LOG.error("读取文件成功："+ins.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String line;
			int lintNum = 0;
			while ((line = reader.readLine()) != null) {
				if (lintNum == 0) {
					lintNum++;
					continue;
				}
				try {
					String[] hrInfo = line.split("\\t");
					if (fileName.equals("office.txt")) {
						if (hrInfo.length != 4)
							continue;
						officeQueue.put(hrInfo);
						LOG.error("部门信息："+hrInfo[1]);
					} else if (fileName.equals("user.txt")) {
						if(hrInfo.length == 8 && "0".equals(hrInfo[7])){
							userQueue.put(hrInfo);
							LOG.error("员工名称："+hrInfo[2]);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			reader.close();
			if (ins != null) {
				ins.close();
				ftp.completePendingCommand();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
