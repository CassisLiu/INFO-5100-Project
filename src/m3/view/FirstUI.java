package m3.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import m3.model.Incentive;
import m3.model.IncentiveList;


public class FirstUI {
	
	JFrame frame;
	JTable table = new JTable(){
		public boolean isCellEditable(int row, int column){
			return false;
		}
	};
	JButton create,delete,edit;
	SecondUI sui ;
	
    public void start(){
        frame = new JFrame();
        frame.setTitle("Incentive List");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponent(panel);
        addListeners();
        frame.setVisible(true);
    }

    private void addListeners() {
    	create.addActionListener(e -> {
    		frame.setEnabled(false);
    		sui= new SecondUI(this, -1);
        	sui.start();
        });
    	  // need to store to an incentive object
    	edit.addActionListener(e -> editSelectedRow());
    	delete.addActionListener(e -> deleteSelectedRow());			
	}

	private void placeComponent(JPanel panel) {
        panel.setLayout(null);
        create = new JButton("Create");
        create.setBounds(50, 25, 80, 25);
        panel.add(create);
        edit = new JButton("Edit");
        edit.setBounds(180, 25, 80, 25);
        panel.add(edit);
        delete = new JButton("Delete");
        delete.setBounds(310, 25, 80, 25);
        panel.add(delete);
        
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(50, 65, 350, 400);
        panel.add(pane);
    }
    
    
    public void refreshTableContents() {
    	DefaultTableModel tableModel = new DefaultTableModel();
    	tableModel.addColumn("Title");
        tableModel.addColumn("Start Date");
        tableModel.addColumn("End Date");
        tableModel.addColumn("Disclaimer");
        
        for(Incentive i : IncentiveList.getAllIncentives()){
    		tableModel.addRow(new String[] {i.getTitle(), i.getStartDate().toString(), i.getEndDate().toString(), i.getDisclaimer()});
    	}
    	
    	table.setModel(tableModel);
	}

    	
    private void editSelectedRow() {
		try{
			int rowIndex = table.getSelectedRow();
			if(rowIndex == -1){
				throw new Exception();
			}
			frame.setEnabled(false);
    		sui= new SecondUI(this,rowIndex);
        	sui.start();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Please select a Row or No rows to Edit");
		}
	}

	private void deleteSelectedRow(){
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		try{
			int rowIndex = table.getSelectedRow();
			tableModel.removeRow(rowIndex);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Please select a Row or No rows to delete");
		}
    }

}
