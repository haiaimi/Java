import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomsFrame extends JFrame implements ActionListener
{
	private JList list_rooms; 
	String Rooms[]= {"501","502","503","504"};
	
	public RoomsFrame()
	{
		super("客房管理");
		
		this.setSize(400,300);
        this.setLocation(200,140);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JMenu menu_query = new JMenu("查询");               //菜单
        JMenuItem menuitem_pre = new JMenuItem("预定");
        JMenuItem menuitem_used = new JMenuItem("入住");
        JMenuItem menuitem_rest = new JMenuItem("空闲");
        menu_query.add(menuitem_pre);
        menuitem_pre.addActionListener(this);
        menu_query.add(menuitem_used);
        menuitem_used.addActionListener(this);
        menu_query.add(menuitem_rest);
        menuitem_rest.addActionListener(this);
        
        JMenu menu_datachange = new JMenu("数据修改");               //菜
        JMenuItem menuitem_add = new JMenuItem("添加");
        JMenuItem menuitem_delete = new JMenuItem("删除");
        JMenuItem menuitem_update = new JMenuItem("修改");
        menu_datachange.add(menuitem_add);
        menuitem_add.addActionListener(this);
        menu_datachange.add(menuitem_delete);
        menuitem_delete.addActionListener(this);
        menu_datachange.add(menuitem_update);
        menuitem_update.addActionListener(this);
        
        JMenuBar menubar = new JMenuBar();                 //菜单栏
        this.setJMenuBar(menubar);     
        menubar.add(menu_query);                            //菜单栏中加入菜单
        menubar.add(menu_datachange);                            //菜单栏中加入菜单
        
        list_rooms = new JList(Rooms);
        this.getContentPane().add(list_rooms);
        
        this.setVisible(true);
	}
	
	 public void actionPerformed(ActionEvent e)
	 {
		 
	 }
	
	public static void main(String arg[])
	{
		new RoomsFrame();
	}
	
}
