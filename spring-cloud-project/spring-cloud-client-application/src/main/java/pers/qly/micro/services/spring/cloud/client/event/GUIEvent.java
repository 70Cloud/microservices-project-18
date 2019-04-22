package pers.qly.micro.services.spring.cloud.client.event;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: NoNo
 * @Description: 监听器基本模式：访问限定符为 public，没有返回值，没有抛异常，Spring 的 @EventListener 例外
 * @Date: Create in 15:33 2019/1/10
 */
public class GUIEvent {

    public static void main(String[] args) {
        JFrame frame = new JFrame("简单 GUI 程序 - Java事件");
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("[%s]事件:%s\n", Thread.currentThread().getName(), e);
            }
        });

        frame.setBounds(300, 300, 400, 300);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
