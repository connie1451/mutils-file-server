package cn.minsin.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MutilsFileConfig {
	
	@Value("${mutils.save-path-linux}")
	private String linux;
	@Value("${mutils.save-path-windows}")
	private String windows;
	@Value("${mutils.save-path-macos}")
	private String macOs;
	
	@Value("${mutils.server-url}")
	private String serverUrl;
	
	
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getLinux() {
		return linux;
	}
	public void setLinux(String linux) {
		this.linux = linux;
	}
	public String getWindows() {
		return windows;
	}
	public void setWindows(String windows) {
		this.windows = windows;
	}
	public String getMacOs() {
		return macOs;
	}
	public void setMacOs(String macOs) {
		this.macOs = macOs;
	}
	
	
}
