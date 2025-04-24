import java.awt.EventQueue;

import java.sql.*;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	private JFrame frame;
	private JTextField tfBookName;
	private JTextField tfEdition;
	private JTextField tfPrice;
	private JTable table;
	private JTextField tfSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	DefaultTableModel model;
	ResultSetMetaData rd;
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookshop","root","");
			JOptionPane.showMessageDialog(null, "Connection success");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void table_load()
	{
		int a;
		try {
			pst=con.prepareStatement("select * from book");
			rs=pst.executeQuery();
			//table.setModel(DbUtils.resultSetToTableModel(rs));
			
			rd=rs.getMetaData();
			a=rd.getColumnCount();
			model=(DefaultTableModel)table.getModel();
			model.setRowCount(0);
			
			while(rs.next())
			{
				Vector v =new Vector();
				for(int i=1;i<=a;i++)
				{
					v.add(rs.getString("id"));
					v.add(rs.getString("name"));
					v.add(rs.getString("edition"));
					v.add(rs.getString("price"));
				}
				model.addRow(v);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 514, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(165, 24, 119, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 65, 254, 128);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 25, 97, 27);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price :");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 89, 97, 27);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Edition :");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 58, 97, 27);
		panel.add(lblNewLabel_1_1_1);
		
		tfBookName = new JTextField();
		tfBookName.setBounds(108, 30, 113, 20);
		panel.add(tfBookName);
		tfBookName.setColumns(10);
		
		tfEdition = new JTextField();
		tfEdition.setColumns(10);
		tfEdition.setBounds(108, 63, 113, 20);
		panel.add(tfEdition);
		
		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(108, 94, 113, 20);
		panel.add(tfPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name;
				int edition,price;
				
				name=tfBookName.getText();
				edition=Integer.parseInt(tfEdition.getText());
				price=Integer.parseInt(tfPrice.getText());
				
				try {
					pst=con.prepareStatement("insert into book(name,edition,price)"
							+ "values(?,?,?)");
					pst.setString(1, name);
					pst.setInt(2, edition);
					pst.setInt(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Kayıt eklendi.");
					table_load();
					tfBookName.setText("");
					tfEdition.setText("");
					tfPrice.setText("");
					tfBookName.requestFocus();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(10, 204, 78, 44);
		frame.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(98, 204, 78, 44);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tfBookName.setText("");
				tfEdition.setText("");
				tfPrice.setText("");
				tfBookName.requestFocus();
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBounds(186, 204, 78, 44);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 70, 214, 122);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Edition", "Price"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price,bid;
				bname=tfBookName.getText();
				edition=tfEdition.getText();
				price=tfPrice.getText();
				bid=tfSearch.getText();
				
				try {
					pst=con.prepareStatement("update book set name=?,edition=?,price=? "
							+ "where id=?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Kayıt bilgileri guncellendi.");
					table_load();
					tfBookName.setText("");
					tfEdition.setText("");
					tfPrice.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(274, 204, 96, 44);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				bid=tfSearch.getText();
				
				try {
					pst=con.prepareStatement("delete from book where id=?");
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Kayıt bilgileri silindi.");
					table_load();
					tfBookName.setText("");
					tfEdition.setText("");
					tfPrice.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(380, 204, 96, 44);
		frame.getContentPane().add(btnDelete);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 259, 254, 71);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("Search ID:");
		lblNewLabel_1_2.setBounds(10, 25, 89, 17);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_1_2);
		
		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				int id=Integer.parseInt(tfSearch.getText());
				
				try {
					pst=con.prepareStatement("select name,edition,price from book where id=?");
					pst.setInt(1, id);
					rs=pst.executeQuery();
					
					if(rs.next()==false)
					{
						JOptionPane.showMessageDialog(null, "ID sahip kitap yok");
					}
					else
					{
						String name=rs.getString("name");
						String edition=rs.getString("edition");
						String price=rs.getString("price");
						
						tfBookName.setText(name);
						tfEdition.setText(edition);
						tfPrice.setText(price);
						//tfPhone.setText("");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		tfSearch.setBounds(104, 24, 86, 20);
		tfSearch.setColumns(10);
		panel_1.add(tfSearch);
	}
}
