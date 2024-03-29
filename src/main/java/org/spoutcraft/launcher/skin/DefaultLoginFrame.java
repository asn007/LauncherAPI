/*
 * This file is part of LauncherAPI (http://www.spout.org/).
 *
 * LauncherAPI is licensed under the SpoutDev License Version 1.
 *
 * LauncherAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * LauncherAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package org.spoutcraft.launcher.skin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.spoutcraft.launcher.Settings;
import org.spoutcraft.launcher.api.Event;
import org.spoutcraft.launcher.api.Launcher;
import org.spoutcraft.launcher.api.OptionsFrame;
import org.spoutcraft.launcher.api.skin.Skin;
import org.spoutcraft.launcher.api.skin.gui.HyperlinkJLabel;
import org.spoutcraft.launcher.api.skin.gui.LoginFrame;
import org.spoutcraft.launcher.api.util.ImageUtils;
import org.spoutcraft.launcher.api.util.Resources;
import org.spoutcraft.launcher.api.util.Utils;

public class DefaultLoginFrame extends LoginFrame implements ActionListener, KeyListener, WindowListener {

	private static final long serialVersionUID = 1797546961340465149L;
	private JPanel contentPane = new JPanel();
	private Container loginPane = new Container();
	private Container offlinePane = new Container();
	public JProgressBar progressBar;
	private JPasswordField passwordField;
	private JComboBox usernameField = new JComboBox();
	private JButton loginButton = new JButton("Login");
	private JButton optionsButton = new JButton("Options");
	private JCheckBox rememberCheckbox = new JCheckBox("Remember");
	private JButton loginSkin1;
	private List<JButton> loginSkin1Image;
	private JButton loginSkin2;
	private List<JButton> loginSkin2Image;

	// Fonts \\
	private Font arial11 = new Font("Arial", Font.PLAIN, 11);
	private Font arial12 = new Font("Arial", Font.PLAIN, 12);
	private Font arial14 = new Font("Arial", Font.PLAIN, 14);

	public DefaultLoginFrame(Skin parent) {
		super(parent);
		setTitle("Spoutcraft Launcher");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Resources.spoutcraftFavIcon));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dim.width - 860) / 2, (dim.height - 500) / 2, 860, 500);
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		loginPane.setBounds(473, 362, 372, 99);

		loginButton.setFont(arial11);
		loginButton.setBounds(272, 13, 86, 23);
		loginButton.setOpaque(false);

		loginButton.addActionListener(this);
		loginButton.setEnabled(false);

		optionsButton.setFont(arial11);
		optionsButton.setOpaque(false);
		optionsButton.addActionListener(this);

		usernameField.setFont(arial11);
		usernameField.addActionListener(this);
		usernameField.setOpaque(false);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(8, 0, 294, 99);
		lblLogo.setIcon(new ImageIcon(Resources.spoutcraftLogo));

		JLabel lblMinecraftUsername = new JLabel("Minecraft Username: ");
		lblMinecraftUsername.setFont(arial11);
		lblMinecraftUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMinecraftUsername.setBounds(-17, 17, 150, 14);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(arial11);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(33, 42, 100, 20);

		passwordField = new JPasswordField();
		passwordField.setFont(arial11);
		passwordField.setBounds(143, 42, 119, 22);

		loginSkin1 = new JButton("Login as Player");
		loginSkin1.setFont(arial11);
		loginSkin1.setBounds(72, 428, 119, 23);
		loginSkin1.setOpaque(false);
		loginSkin1.addActionListener(this);
		loginSkin1.setVisible(false);
		loginSkin1Image = new ArrayList<JButton>();

		loginSkin2 = new JButton("Login as Player");
		loginSkin2.setFont(arial11);
		loginSkin2.setBounds(261, 428, 119, 23);
		loginSkin2.setOpaque(false);
		loginSkin2.addActionListener(this);
		loginSkin2.setVisible(false);
		loginSkin2Image = new ArrayList<JButton>();

		int loginid = 0;
		for (String user : getSavedUsernames()) {
			if (hasSavedPassword(user)) {
				loginid++;
				if (loginid == 1) {
					loginSkin1.setText(user);
					loginSkin1.setVisible(true);
					ImageUtils.drawCharacter(contentPane, this, "http://s3.amazonaws.com/MinecraftSkins/" + user + ".png", 103, 170, loginSkin1Image);
					loginSkin1.setActionCommand("LoginSkin1");
					for (JButton button : loginSkin1Image) {
						button.setActionCommand("LoginSkin1");
					}
					passwordField.setText(getSavedPassword(user));
					rememberCheckbox.setSelected(true);
				} else if (loginid == 2) {
					loginSkin2.setText(user);
					loginSkin2.setVisible(true);
					ImageUtils.drawCharacter(contentPane, this, "http://s3.amazonaws.com/MinecraftSkins/" + user + ".png", 293, 170, loginSkin2Image);
					loginSkin2.setActionCommand("LoginSkin2");
					for (JButton button : loginSkin2Image) {
						button.setActionCommand("LoginSkin2");
					}
				}
				usernameField.addItem(user);


			}
		}

		progressBar = new JProgressBar();
		progressBar.setBounds(30, 100, 400, 23);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setOpaque(true);

		JLabel purchaseAccount = new HyperlinkJLabel("<html><u>Need a minecraft account?</u></html>", "http://www.minecraft.net/register.jsp");
		purchaseAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		purchaseAccount.setBounds(243, 70, 111, 14);

		purchaseAccount.setText("<html><u>Need an account?</u></html>");
		purchaseAccount.setFont(arial11);
		purchaseAccount.setForeground(new Color(0, 0, 255));
		usernameField.setBounds(143, 14, 119, 25);
		rememberCheckbox.setFont(arial11);

		rememberCheckbox.setOpaque(false);

		final JTextPane editorPane = new JTextPane();
		editorPane.setContentType("text/html");

		AsyncRSSFeed rss = new AsyncRSSFeed(editorPane);
		if (getSavedUsernames().size() > 0)
			rss.setUser(getSavedUsernames().get(0));
		rss.execute();

		editorPane.setEditable(false);
		editorPane.setOpaque(false);

		JLabel trans2;

		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setBounds(473, 11, 372, 340);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		editorPane.setCaretPosition(0);
		trans2 = new JLabel();
		trans2.setBackground(new Color(229, 246, 255, 100));
		trans2.setOpaque(true);
		trans2.setBounds(473, 11, 372, 340);

		JLabel login = new JLabel();
		login.setBackground(new Color(255, 255, 255, 120));
		login.setOpaque(true);
		login.setBounds(473, 362, 372, 99);

		JLabel trans;
		trans = new JLabel();
		trans.setBackground(new Color(229, 246, 255, 60));
		trans.setOpaque(true);
		trans.setBounds(0, 0, 854, 480);

		usernameField.getEditor().addActionListener(this);
		passwordField.addKeyListener(this);
		rememberCheckbox.addKeyListener(this);

		usernameField.setEditable(true);
		contentPane.setLayout(null);
		rememberCheckbox.setBounds(144, 66, 93, 23);
		contentPane.add(lblLogo);
		optionsButton.setBounds(272, 41, 86, 23);
		contentPane.add(loginSkin1);
		contentPane.add(loginSkin2);

		loginPane.setBounds(473, 362, 372, 99);
		loginPane.add(lblPassword);
		loginPane.add(lblMinecraftUsername);
		loginPane.add(passwordField);
		loginPane.add(usernameField);
		loginPane.add(loginButton);
		loginPane.add(rememberCheckbox);
		loginPane.add(purchaseAccount);
		loginPane.add(optionsButton);
		contentPane.add(loginPane);

		JLabel offlineMessage = new JLabel("Could not connect to minecraft.net");
		offlineMessage.setFont(arial14);
		offlineMessage.setBounds(25, 40, 217, 17);

		JButton tryAgain = new JButton("Try Again");
		tryAgain.setOpaque(false);
		tryAgain.setFont(arial12);
		tryAgain.setBounds(257, 20, 100, 25);

		JButton offlineMode = new JButton("Offline Mode");
		offlineMode.setOpaque(false);
		offlineMode.setFont(arial12);
		offlineMode.setBounds(257, 52, 100, 25);

		offlinePane.setBounds(473, 362, 372, 99);
		offlinePane.add(tryAgain);
		offlinePane.add(offlineMode);
		offlinePane.add(offlineMessage);
		offlinePane.setVisible(false);
		contentPane.add(offlinePane);

		contentPane.add(scrollPane);
		contentPane.add(trans2);
		contentPane.add(login);
		contentPane.add(trans);
		contentPane.add(progressBar);

		final JLabel background = new JLabel("Loading...");
		background.setVerticalAlignment(SwingConstants.CENTER);
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0, 854, 480);
		contentPane.add(background);

		File cacheDir = new File(Utils.getWorkingDirectory(), "cache");
		cacheDir.mkdirs();
		File backgroundImage = new File(cacheDir, "launcher_background.jpg");
		(new BackgroundImageWorker(backgroundImage, background)).execute();

		Vector<Component> order = new Vector<Component>(5);
		order.add(usernameField.getEditor().getEditorComponent());
		order.add(passwordField);
		order.add(rememberCheckbox);
		order.add(loginButton);
		order.add(optionsButton);

		setFocusTraversalPolicy(new SpoutFocusTraversalPolicy(order));

		addWindowListener(this);

		loginButton.setEnabled(true);
	}

	public void init() {
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("login")) {
			doLogin(usernameField.getSelectedItem().toString(), new String(passwordField.getPassword()));
			if (rememberCheckbox.isSelected())
				saveUsername(usernameField.getSelectedItem().toString(), new String(passwordField.getPassword()));
		} else if (e.getActionCommand().equals(loginSkin1.getActionCommand())) {
			doLogin(loginSkin1.getText());
		} else if (e.getActionCommand().equals(loginSkin2.getActionCommand())) {
			doLogin(loginSkin2.getText());
		} else if (e.getActionCommand().equals(optionsButton.getActionCommand())) {
			OptionsFrame options = Launcher.getOptionsDialog();
			options.setVisible(true);
			options.setBounds((int) Math.max(1, getBounds().getCenterX() - (320 / 2)), (int) Math.max(1, getBounds().getCenterY() - (365 / 2)), 320, 365);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (loginButton.isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			doLogin(usernameField.getSelectedItem().toString(), new String(passwordField.getPassword()));
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void onEvent(Event event) {
		switch (event) {
			case BAD_LOGIN:
				JOptionPane.showMessageDialog(getParent(), "Incorrect usernameField/passwordField combination");
				break;
			case FINISHED_UPDATE_CHECK:
				if (Launcher.getGameUpdater().isInitialInstall() || Settings.isAcceptUpdates()) {
					runUpdater();
				} else if (isMinecraftUpdateaAvailable()) {
					int result = JOptionPane.showConfirmDialog(this, "There is an update available for Minecraft. Would you like to update?", "Minecraft Update", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						runUpdater();
					} else {
						Launcher.getGameUpdater().runValidator();
					}
				} else if (isSpoutcraftUpdateaAvailable()) {
					int result = JOptionPane.showConfirmDialog(this, "There is an update available for Spoutcraft. Would you like to update?", "Spoutcraft Update", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						runUpdater();
					} else {
						Launcher.getGameUpdater().runValidator();
					}
				} else {
					Launcher.getGameUpdater().runValidator();
				}

				break;
			case MINECRAFT_NETWORK_DOWN:
				if (!canPlayOffline()) {
					JOptionPane.showMessageDialog(getParent(), "Unable to authenticate account with minecraft.net");
				} else {
					int result = JOptionPane.showConfirmDialog(getParent(), "Would you like to run in offline mode?", "Unable to Connect to Minecraft.net", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						Launcher.getGameLauncher().runGame("Player", "", "", "");
					}
				}
				break;
			case GAME_LAUNCH_SUCCESS:
				break;
			case GAME_LAUNCH_FAILED:
				break;
			case SUCESSFUL_LOGIN:
				break;
			case UPDATE_FINISHED:
				break;
			case USER_NOT_PREMIUM:
				JOptionPane.showMessageDialog(getParent(), "You purchase a minecraft account to play");
				break;
			case UPDATE_FAILED:
				JOptionPane.showMessageDialog(getParent(), new StringBuilder().append("Oh no! The ").append(isMinecraftUpdateaAvailable() && isSpoutcraftUpdateaAvailable() ? "Minecraft and Spoutcraft" : isSpoutcraftUpdateaAvailable() ? "Spoutcraft" : "Minecraft").append(" update failed!").toString());
				break;
			case VALIDATION_PASSED:
				Launcher.getGameUpdater().runGame();
				break;
			case VALIDATION_FAILED:
				break;
		}
	}

	public void stateChanged(String status, float progress) {
		int intProgress = Math.round(progress);

		progressBar.setValue(intProgress);
		if (status.length() > 60) {
			status = status.substring(0, 60) + "...";
		}
		progressBar.setString(intProgress + "% " + status);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
		Launcher.getOptionsDialog().exit();
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
