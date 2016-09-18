package cn.bigforce.tool.encodingconverter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import sun.tools.jar.resources.jar;

/**
 * 转换器GUI
 * @author bigforce
 *
 */
public class ConverterUI extends JFrame {
	/**
	 * First version
	 */
	private static final long serialVersionUID = 1L;
	JTextField source;
	JTextField suffix;
	JRadioButton gbk_utf8;
	JRadioButton utf8_gbk;

	public ConverterUI() {
		super("GBK<->UTF8 converter");
		setLayout(new GridLayout(7, 1, 10, 10));
		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		add(new JLabel("Choose a folder..."));

		JButton button = new JButton("choose");
		add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int d = fileChooser.showOpenDialog(null);
				if (d == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					source.setText(file.getPath());
				}
			}
		});

		source = new JTextField();
		add(source);

		ButtonGroup bg = new ButtonGroup();
		gbk_utf8 = new JRadioButton("GBK==>UTF8");
		utf8_gbk = new JRadioButton("UTF8==>GBK");
		bg.add(gbk_utf8);
		bg.add(utf8_gbk);
		add(gbk_utf8);
		add(utf8_gbk);

		suffix = new JTextField(".java");
		add(suffix);
		JButton button2 = new JButton("start");
		add(button2);
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (gbk_utf8.isSelected()) {
					fileVisit(new File(source.getText()), new MyFileNameFilter(suffix.getText()),
							new Converter(Converter.GBK_UTF8));
				} else if (utf8_gbk.isSelected()) {
					fileVisit(new File(source.getText()), new MyFileNameFilter(suffix.getText()),
							new Converter(Converter.UTF8_GBK));
				}
			}
		});

	}
	/**
	 * 深度搜索访问所有文件
	 * @param file 文件
	 * @param filter 文件过滤器
	 * @param converter 编码转换器对象
	 */
	private void fileVisit(File file, FileFilter filter, Converter converter) {
		if (file.isDirectory()) {
			for (File f : file.listFiles(filter)) {
				fileVisit(f, filter, converter);
			}
		} else {
			try {
				converter.process(file);
			} catch (Exception e) {
				Object [] o= {e.getMessage()};
				JOptionPane.showMessageDialog(null, o ,"ERROR",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("Substance Raven Graphite failed to initialize");
		}

		ConverterUI converterUI = new ConverterUI();
		converterUI.setVisible(true);
	}
}
