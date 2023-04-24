import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class Menu_principal {

	private JFrame frame;
	private JTextField txtLibellé;
	private JTextField txtDescription;
	private JTextField txtDate;
	private JTable txttable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_principal window = new Menu_principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
Connection con;
PreparedStatement pst;
ResultSet rs;
private JTextField txtQuantité;
private JTextField txtClient;
private JTextField txtdate;
private JTextField txtquantite;
private JTextField txtProduit;
private JTextField txtPrix_unitaire;
private JTextField txtPrix_total;
private JTable txttablecommande;

	public Menu_principal() throws SQLException {
		initialize();
        Table();
        Tablecommande();
	}

	
	
	
	public void Connect() {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/gestion_stock", "root", "");
		System.out.println("connexion établie");
	} catch (Exception e) {
		e.printStackTrace();
	} 
	}
	// Affichage des éléments de la base de données c'est à dire les produits en stock
	private void Table() 	{
		try {
			Connect();
			String [] entete = {"Id", "Libellé", "Description", "Quantité", "Date_de_péremption"};
	        String [] ligne = new String[6];
	        
	        DefaultTableModel model = new DefaultTableModel(null, entete);
	        
	        
	        Statement st = con.createStatement();
	        rs = st.executeQuery("SELECT * FROM produit");
	        
	while(rs.next()) {
		ligne[0] = rs.getString("Id");
		ligne[1] = rs.getString("Libellé");
		ligne[2] = rs.getString("Description");
		ligne[3] = rs.getString("Quantité");
		ligne[4] = rs.getString("Date_de_péremption");
	    model.addRow(ligne);
		}
	     txttable.setModel(model);
		} catch (Exception e) {
			
		}
		
	}
	
	// Affichage de la liste des commandes 
	private void Tablecommande() 	{
		try {
			Connect();
			String [] entete2 = {"Client", "Date", "Produit", "Quantité", "Prix_unitaire", "Prix_Total"};
	        String [] ligne2 = new String[7];
	        
	       DefaultTableModel model2 = new DefaultTableModel(null, entete2);  
	        
	        
	        Statement st2 = con.createStatement();
	        rs = st2.executeQuery("SELECT * FROM commande");
	        
	while(rs.next()) {
		ligne2[0] = rs.getString("Client");
		ligne2[1] = rs.getString("Date");
		ligne2[2] = rs.getString("Produit");
		ligne2[3] = rs.getString("Quantité");
		ligne2[4] = rs.getString("Prix_unitaire");
		ligne2[5] = rs.getString("Prix_Total");
		model2.addRow(ligne2);
		}
        txttablecommande.setModel(model2);
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setTitle("Application de Gestion de stock");
		frame.setResizable(false);
		frame.setBounds(100, 30, 722, 659);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 686, 33);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Produits");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(149, 0, 278, 33);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 686, 105);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Libell\u00E9 :");
		lblNewLabel_1_1.setBounds(80, 11, 46, 14);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_3 = new JLabel("Description :");
		lblNewLabel_1_3.setBounds(68, 46, 78, 14);
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Quantit\u00E9 :");
		lblNewLabel_1_4.setBounds(358, 11, 63, 14);
		panel_1.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_2 = new JLabel("Date de peremption :");
		lblNewLabel_1_2.setBounds(317, 46, 123, 14);
		panel_1.add(lblNewLabel_1_2);
		
		txtLibellé = new JTextField();
		txtLibellé.setColumns(10);
		txtLibellé.setBounds(158, 8, 86, 20);
		panel_1.add(txtLibellé);
		
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		txtDescription.setBounds(158, 43, 94, 20);
		panel_1.add(txtDescription);
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(450, 43, 86, 20);
		panel_1.add(txtDate);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
			Connect();
			pst = con.prepareStatement("Insert into produit(Libellé, Description, Quantité, Date_de_péremption) values (?,?,?,?)"); 
			 // Id du produit est geré automatiquement par la base de données
			 pst.setString(1, txtLibellé.getText());
			 pst.setString(2, txtDescription.getText());
			 pst.setString(3, txtQuantité.getText());
			 pst.setString(4, txtDate.getText());
			 pst.executeUpdate();
			 con.close();
			 JOptionPane.showMessageDialog(null,txtLibellé.getText()+" "+"Ajouté(e)s");
			 Table();	
			} catch (Exception e2) {
				// TODO: handle exception
			e2.printStackTrace();
			}
				
				
			}
			
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(232, 71, 89, 23);
		panel_1.add(btnNewButton);
		
		txtQuantité = new JTextField();
		txtQuantité.setColumns(10);
		txtQuantité.setBounds(450, 8, 86, 20);
		panel_1.add(txtQuantité);
				 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 253, 514, -81);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane txttable1 = new JScrollPane();
		txttable1.setBounds(10, 172, 686, 127);
		frame.getContentPane().add(txttable1);
		
		txttable = new JTable();
		txttable1.setViewportView(txttable);
		txttable.setBackground(Color.LIGHT_GRAY);
		txttable.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 320, 686, 33);
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_2);
		
		JLabel lblCommandes = new JLabel("Commandes");
		lblCommandes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommandes.setForeground(Color.BLUE);
		lblCommandes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCommandes.setBounds(156, 0, 294, 33);
		panel_2.add(lblCommandes);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(10, 364, 686, 105);
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Client :");
		lblNewLabel_1_1_1.setBounds(17, 11, 46, 14);
		panel_1_1.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Date :");
		lblNewLabel_1_3_1.setBounds(20, 46, 78, 14);
		panel_1_1.add(lblNewLabel_1_3_1);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Produit :");
		lblNewLabel_1_4_1.setBounds(201, 11, 63, 14);
		panel_1_1.add(lblNewLabel_1_4_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Quantit\u00E9 :");
		lblNewLabel_1_2_1.setBounds(201, 46, 123, 14);
		panel_1_1.add(lblNewLabel_1_2_1);
		
		txtClient = new JTextField();
		txtClient.setColumns(10);
		txtClient.setBounds(73, 8, 86, 20);
		panel_1_1.add(txtClient);
		
		txtdate = new JTextField();
		txtdate.setColumns(10);
		txtdate.setBounds(73, 43, 86, 20);
		panel_1_1.add(txtdate);
		
		txtquantite = new JTextField();
		txtquantite.setColumns(10);
		txtquantite.setBounds(274, 43, 86, 20);
		panel_1_1.add(txtquantite);
		
		JButton btnNewButton_1 = new JButton("Commander");
		  
		// Passer une commande
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
									
				try {

			        PreparedStatement st = con.prepareStatement("SELECT * FROM produit WHERE Libellé=?");
			        st.setString(1, txtProduit.getText());
			        ResultSet rs = st.executeQuery();
					
			        
			    	int bdquantite = 0; 
			    	
					while (rs.next()) {
			            bdquantite = rs.getInt("Quantité");
					}
					
			        int texte_quantite = Integer.parseInt(txtquantite.getText());
			       String texte_nom = txtProduit.getText();
			        if ( texte_quantite > bdquantite) {
			   		 String Newligne=System.getProperty("line.separator");
			                   JOptionPane.showMessageDialog(null, "Il ne reste que "+bdquantite+" en stock.");
			        
			        }
			        
			        else if ( bdquantite==0) {
			        	
				                   JOptionPane.showMessageDialog(null, "Rupture de stock!");
				        
				        } 
			        
			        
			        else if( texte_quantite <= bdquantite) { 

	
					
					Connect();
					PreparedStatement pst = con.prepareStatement("Insert into commande values (?,?,?,?,?,?)"); 
					 pst.setString(1, txtClient.getText());
					 pst.setString(2, txtdate.getText());
					 pst.setString(3, txtProduit.getText());
					 pst.setString(4, txtquantite.getText());
					 pst.setString(5, txtPrix_unitaire.getText());
					 pst.setString(6, txtPrix_total.getText());
					 pst.executeUpdate();
					 
					 PreparedStatement Upst = con.prepareStatement("Update produit set Quantité=Quantité-? where Libellé=?");
						 Upst.setInt(1, texte_quantite);
						 Upst.setString(2, txtProduit.getText());
						 Upst.executeUpdate();
					 JOptionPane.showMessageDialog(null, "Commande effectuée");  
					
					
			        }

			        else  {
			        	JOptionPane.showMessageDialog(null, "Produit introuvable");
				        
			        } 
		 
					 Tablecommande();	
					} catch (Exception e2) {
						// TODO: handle exception
					e2.printStackTrace();
					}	
			
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_1.setBounds(240, 71, 110, 23);
		panel_1_1.add(btnNewButton_1);
		
		
		txtProduit = new JTextField();
		txtProduit.setColumns(10);
		txtProduit.setBounds(274, 8, 86, 20);
		panel_1_1.add(txtProduit);
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("Prix unitaire :");
		lblNewLabel_1_4_1_1.setBounds(384, 11, 75, 14);
		panel_1_1.add(lblNewLabel_1_4_1_1);
		
		JLabel lblNewLabel_1_4_1_1_1 = new JLabel("Prix total :");
		lblNewLabel_1_4_1_1_1.setBounds(407, 46, 63, 14);
		panel_1_1.add(lblNewLabel_1_4_1_1_1);
		
		txtPrix_unitaire = new JTextField();
		txtPrix_unitaire.setColumns(10);
		txtPrix_unitaire.setBounds(480, 8, 86, 20);
		panel_1_1.add(txtPrix_unitaire);
		
		txtPrix_total = new JTextField();
		txtPrix_total.setColumns(10);
		txtPrix_total.setBounds(480, 43, 86, 20);
		panel_1_1.add(txtPrix_total);
		
		JScrollPane txttable2 = new JScrollPane();
		txttable2.setBounds(10, 480, 686, 127);
		frame.getContentPane().add(txttable2);
		
		txttablecommande = new JTable();
		txttablecommande.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txttablecommande.setBackground(Color.LIGHT_GRAY);
		txttable2.setViewportView(txttablecommande);
	}
}
