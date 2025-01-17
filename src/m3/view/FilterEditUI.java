package m3.view;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import m3.model.checker.Checker;
import m3.model.checker.EqualChecker;
import m3.model.checker.GreaterChecker;
import m3.model.checker.LessChecker;
import m3.model.filter.BrandFilter;
import m3.model.filter.ColorFilter;
import m3.model.filter.Filter;
import m3.model.filter.YearFilter;

public class FilterEditUI extends BasicUI{

	private JLabel UIName, NameCombo, TypeCombo, valueInput;
	private JPanel namePanel, UINamePanel, typePanel, valuePanel, buttonPanel;
	private JComboBox Name;
	private JComboBox Type;
	private JTextField value;
	private JButton cancle, ok;	
	private SecondUI sui;
	
	FilterEditUI(SecondUI sui){
		this.sui = sui;
	}
	@Override
	public void create() {
		// TODO Auto-generated method stub
		UIName = new JLabel("Condition");
		Name = new JComboBox(new String[] { "Brand", "Year", "VehicleIDs", "Color" });
		Type = new JComboBox(new String[] {"="});
		NameCombo = new JLabel("name:");
		TypeCombo = new JLabel("type:");
		valueInput = new JLabel("Value:");
		value = new JTextField(10);
		cancle = new JButton("Cancle");
		ok = new JButton("Okay");
		
		
		
	}
	
	
	
	private void addUINamePanel(Container con) {
		UINamePanel = new JPanel();
		con.add(UINamePanel);
	}

	private void addNamePanel(Container con) {
		namePanel = new JPanel();
		namePanel.add(NameCombo);
		namePanel.add(Name);
		con.add(namePanel);
	}
	
	private void addTypePanel(Container con) {
		typePanel = new JPanel();
		typePanel.add(TypeCombo);
		typePanel.add(Type);
		con.add(typePanel);
	}
	
	private void addValuePanel(Container con) {
		valuePanel = new JPanel();
		valuePanel.add(valueInput);
		valuePanel.add(value);
		con.add(valuePanel);
	}
	
	private void addButtonPanel(Container con) {
		buttonPanel = new JPanel();
		buttonPanel.add(cancle);
		buttonPanel.add(ok);
		con.add(buttonPanel);
	}
	private void TypeToBe(String type) {
		switch(type) {
		case("Brand"):{
			Type.removeAllItems();
			Type.addItem("=");
			break;
		}
		case("Year"):{
			Type.removeAllItems();
			Type.addItem(">");
			Type.addItem("<");
			break;
					
		}
		case("VehicleIDs"):
		{
			Type.removeAllItems();
			Type.addItem("=");
			break;
		}
		case("Color"):{
			Type.removeAllItems();
			Type.addItem("=");
			break;
			
		}
		
		
		
		}
	}


	@Override
	public void add(Container con) {
		// TODO Auto-generated method stub
		GridLayout gl = new GridLayout(0, 1);
		con.setLayout(gl);
		con.setName("condition");
		addUINamePanel(con);
		addNamePanel(con);
		addTypePanel(con);
		addValuePanel(con);
		addButtonPanel(con);
		
	}


	
	@Override
	public void addListeners() {
		Name.addActionListener((e) -> TypeToBe(Name.getSelectedItem().toString())
		);
		
		cancle.addActionListener((e) -> this.dispose());
		ok.addActionListener((e) -> {addFilter(Name.getSelectedItem().toString(),Type.getSelectedItem().toString(), value.getText());
										sui.addToTableBelow(toSecondUIFilter());
										this.dispose();
										
		System.out.println(addFilter(Name.getSelectedItem().toString(),Type.getSelectedItem().toString(), value.getText()).checkerToString());
		});
	}
	
	
	
	private Filter addFilter(String name, String type, String value) {
		return FilterFinder(name, type, value);
	}
	
	public Filter toSecondUIFilter()
	{
		return addFilter(Name.getSelectedItem().toString(),Type.getSelectedItem().toString(), value.getText());
		
	}
	
	
	
	private Filter FilterFinder(String name, String type, String value) {
		switch(name) {
		case("Brand"):{
				return new BrandFilter(value, (Checker) new EqualChecker());
		}
		case("Color"):{
			return new ColorFilter(value, (Checker)new EqualChecker());
			
		}
		case("VehicleIDs"):{
			//return new VehicleIDsFilter(value, new EqualChecker());
		}
		case("Year"):{
			if(type.equals("<"))
				return new YearFilter(value,(Checker) new GreaterChecker()); 
				
			else
				
				return new YearFilter(value, (Checker)new LessChecker());
		}
		}
		return null;
	}
	
	public void modifyFilter(Filter f) {
		
	}
	
	
	

}
