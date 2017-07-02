package excel.app;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SplashScreen;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.URI;
import java.nio.charset.Charset;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	private static Log logger = LogFactory.getLog(Application.class);
	private static ConfigurableApplicationContext app;

	private static SplashScreen splash;

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Throwable t) {
			logger.error("failed to start App", t);
		}
	}

	private static void start(String[] args) {
		if (!SystemTray.isSupported()) {
			logger.warn("SystemTray is not supported");
			startSpring(args);
			return;
		} else {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				logger.error("UIManager error", e);
			}
			showSplash();
			startSpring(args);
			showTray();
			try {
				System.setProperty("file.encoding", "UTF-8");
				Field charset = Charset.class.getDeclaredField("defaultCharset");
				charset.setAccessible(true);
				charset.set(null, null);
			} catch (Exception e) {
				logger.error(e);
			}
			if (splash != null) {
				splash.close();
			}
		}
	}

	/**
	 * @see https://docs.oracle.com/javase/tutorial/uiswing/misc/splashscreen.html
	 */
	private static void showSplash() {
		splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			logger.info("SplashScreen.getSplashScreen() returned null");
			return;
		}
		Graphics2D g = splash.createGraphics();
		if (g == null) {
			logger.info("splash.createGraphics() is null");
			return;
		}
	}

	private static void startSpring(String[] args) {
		try {
			app = SpringApplication.run(Application.class, args);
		} catch (Exception e) {
			logger.error("fail to start spring boot", e);
		}
	}

	private static void showTray() {
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("image/icon.gif");

		PopupMenu trayPopupMenu = new PopupMenu();
		MenuItem openBrowserAction = new MenuItem("Open Browser");
		openBrowserAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String ip = Inet4Address.getLocalHost().getHostAddress();
					String port = app.getEnvironment().getProperty("local.server.port");
					if (port.equals("80")) {
						Desktop.getDesktop().browse(new URI("http://" + ip));
					} else {
						Desktop.getDesktop().browse(new URI("http://" + ip + ":" + port));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		trayPopupMenu.add(openBrowserAction);
		trayPopupMenu.add("About " + Constants.APP_NAME);

		MenuItem exitAction = new MenuItem("Exit");
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					app.close();
					logger.info("ended application");
				} finally {
					System.exit(0);
				}
			}
		});
		trayPopupMenu.addSeparator();
		trayPopupMenu.add(exitAction);

		TrayIcon trayIcon = new TrayIcon(image, Constants.APP_NAME, trayPopupMenu);
		trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			logger.error(awtException);
		}
	}
}
