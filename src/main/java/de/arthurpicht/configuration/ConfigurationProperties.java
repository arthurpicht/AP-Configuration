package de.arthurpicht.configuration;

import java.io.PrintStream;
import java.util.*;

public class ConfigurationProperties {
	
	private final String sectionName;
	
	private final Map<String, List<String>> configurationPropertiesMap;
//	private Map<String, OrderedTree<HierarchyContainer>> hierarchyTreeMap;
	
	public ConfigurationProperties(String sectionName, List<String> configLines) {
		
		this.sectionName = sectionName;
		
		this.configurationPropertiesMap = new LinkedHashMap<>();
//		this.hierarchyTreeMap = new HashMap<String, OrderedTree<HierarchyContainer>>();
		
		for (String configLine : configLines) {
			// Analysiere und verarbeite Zuweisungen
			this.analyzeConfigLine(configLine);
		}
		
		// Löse die Referenz von Variablen innerhalb der Konfigurationswerte auf.
		ValueExpander.expandVariables(this);
		
		// Hierarchie-Baum aufbauen
//		this.buildTreeHierarchyStarter();
	}
	
	/**
	 * Privater Konstruktor, von getDeepCopy genutzt.
	 * @param sectionName
	 * @param configurationPropertiesMap
	 */
	private ConfigurationProperties(String sectionName, Map<String, List<String>> configurationPropertiesMap) {
		this.sectionName = sectionName;
		this.configurationPropertiesMap = configurationPropertiesMap;
	}
	
	/**
	 * Erstellt eine tiefe Kopie diese Objektes mit Übernahme des übergebenen Namens.
	 *  
	 * @param sectionName
	 * @return
	 */
	public ConfigurationProperties getDeepCopy(String sectionName) {
		
		Map<String, List<String>> configurationPropertiesMapCopy = new HashMap<String, List<String>>();
		
		Set<String> keys = this.configurationPropertiesMap.keySet();
		for (String key : keys) {
			
			List<String> valuesCopy = new ArrayList<String>();
			
			List<String> values = this.configurationPropertiesMap.get(key);
			for (String value : values) {
				String valueCopy = new String(value);
				valuesCopy.add(valueCopy);
			}		
			
			configurationPropertiesMapCopy.put(new String(key), valuesCopy);
		}
		
		return new ConfigurationProperties(sectionName, configurationPropertiesMapCopy);
		
	}
	
	public String getSectionName() {
		return this.sectionName;
	}
	
	/**
	 * Liefert den Konfigrationswert zu einer übergebenen Konfigurationsvariablen.
	 * Wenn zur gegebenen Konfigurationsvariablen kein Wert vorliegt wird 'null'
	 * zurück gegeben.
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		
//		System.out.println("Suche property zu key :'" + key + "'");
//		System.out.println("Alle Keys:");
//		Set<String> keys = this.configurationProperties.keySet();
//		for (String debugKey : keys) {
//			System.out.println(debugKey + " equals? " + (debugKey.equals(key)));
//		}
//		System.out.println("Ende alle Keys");
		
		List<String> valueList = this.configurationPropertiesMap.get(key);
		if (valueList == null || valueList.isEmpty()) {
			// todo debug
			return null;
		}
		return valueList.get(0);
	}
	
	/**
	 * Liefert die Konfigurationswertliste zu einer übergebenen Konfigurationsvariablen.
	 * Wenn zur gegebenen Konfigurationsvariablen kein Wert vorliegt wird 'null'
	 * zurück gegeben.
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getPropertyList(String key) {
		List<String> valueList = this.configurationPropertiesMap.get(key);
		if (valueList == null || valueList.isEmpty()) {
			return null;
		}
		return valueList;
	}
	
	/**
	 * Liefert alle Variablenbezeichnungen dieser Konfiguration zurück.
	 * 
	 * @return
	 */
	public Set<String> getKeys() {
		return this.configurationPropertiesMap.keySet();
	}
	
	/**
	 * Prüft ob übergebene Variablenbezeichnung Teil dieser Konfiguration ist.
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return this.configurationPropertiesMap.containsKey(key);
	}
	
	/**
	 * Fügt bzw. ersetzt übergebenes Paar von Konfigurationsvariable/
	 * Konfigurationswerteliste.
	 * 
	 * @param key
	 * @param valueList
	 */
	public void put(String key, List<String> valueList) {
		this.configurationPropertiesMap.put(key, valueList);
	}
	
	/**
	 * Überschreibt diese Konfiguration mit der übergebenen Konfiguration.
	 * 
	 * @param configurationProperties
	 */
	public void override(ConfigurationProperties configurationProperties) {
		Set<String> keys = configurationProperties.getKeys();
		for (String key : keys) {
			List<String> propertyListOverride = configurationProperties.getPropertyList(key);
			// Es werden alle Key/PropertyList-Paare per 'put' in die Tabelle geschoben.
			// Wenn der Schlüssel vorbestehend ist, wird der Wert überschrieben, sonst
			// neu angelegt.
			this.configurationPropertiesMap.put(key, propertyListOverride);
		}
		
		// Hierarchie-Baum neu aufbauen
//		this.hierarchyTreeMap = new HashMap<String, OrderedTree<HierarchyContainer>>();
//		this.buildTreeHierarchyStarter();
	}
	
//	public OrderedTree<HierarchyContainer> getHierarchyTree(String rootElement) {
//		OrderedTree<HierarchyContainer> orderedTree = this.hierarchyTreeMap.get(rootElement);
//		
//		// TODO debug
////		System.out.println("**************************************************");
////		System.out.println("ConfProp.getHierarchyTree");
////		System.out.println("root: " + rootElement);		
////		System.out.println("orderedTree == null ? " + (orderedTree == null));
////		System.out.println("keys: " + this.hierarchyTreeMap.keySet());
//		
//		return orderedTree;
//	}
	
	/**
	 * Konsolenausgabe der Konfiguration. Nur zu Testzwecken.
	 *
	 */
	public void list(PrintStream out) {
		out.println("-- ConfigurationProperties for section [" + this.sectionName + "] --");
		Set<String> keys = this.getKeys();
		for (String key : keys) {
			out.print("'" + key + "' = ");
			List<String> valueList = this.getPropertyList(key);
			if (valueList.size() == 1) {
				out.println(valueList.get(0));
			} else {
				out.println();
				for (String value : valueList) {
					out.println(" " + value);
				}
			}
		}
	}
	
	/**
	 * Prüft ob es sich bei der übergebenen Zeile, die keine Leerzeile und kein Kommentar ist,
	 * um eine Zuweisung eines Wertes zu einem Namen handelt. Wenn dies so ist, dann wird diese
	 * Zuweisung analysiert und verarbeitet.
	 *  
	 * @param configLine
	 */
	private void analyzeConfigLine(String configLine) {
		String[] partsOfLine = configLine.split(Character.toString(Parameters.getAssignmentOperator()), 2);
		
		String propertyName = partsOfLine[0].trim();
		String propertyValue = partsOfLine[1].trim();
		
		if (!propertyName.isEmpty() && !propertyValue.isEmpty()) {
			// Zerlege den Variablenwert in Listelemente. Genau ein Element, wenn es sich
			// um eine einfache Zuweisung handelt. Viele Elemente, wenn es sich um eine
			// 'Single-Line-Liste' handelt.
			List<String> valueListElements = getSingleLineListElements(propertyValue);
			
			// Prüfen ob diese Konfigurationsvariable schon vorliegt
			List<String> valueList = this.configurationPropertiesMap.get(propertyName);
			if (valueList != null) {
				// Es liegt schon ein Property mit diesem Namen vor. Füge Wert als
				// weiteres Element der Liste hinzu
				valueList.addAll(valueListElements);
			} else {
				// Diese Konfigurationsvariable existiert noch nicht.
				// Füge sie hinzu
				valueList = new ArrayList<>();
				valueList.addAll(valueListElements);
				this.configurationPropertiesMap.put(propertyName, valueList);
			}			
		}
				
	}
	
//	private void buildTreeHierarchyStarter() {
//		for (String key : this.configurationPropertiesMap.keySet()) {
//			List<String> valueList = this.configurationPropertiesMap.get(key);
//			this.buildTreeHierarchy(key, valueList);
//		}
//		
//		// TODO debug
////		System.out.println("TreeMapKeys: " + this.hierarchyTreeMap.keySet());
//		
//	}
	
//	private void buildTreeHierarchy(String propertyName, List<String> valueList) {
//		
////		System.out.println("Baue Baum für Conf-Name  " + propertyName + " Wert: "  + valueList);
//		
//		// Die Konfigurationsvariable (propertyName) anhand des Hierarchie-Trennzeichens (hierarchyDelimiter) 
//		// in Einzelabschnitte (hierarchyNameTokens) zerlegen.
//		StringTokenizer stringTokenizer = new StringTokenizer(propertyName, Character.toString(Parameters.getHierarchyDelimiter())); 
//		List<String> hierarchyNameTokens = new ArrayList<String>();
//		while(stringTokenizer.hasMoreTokens()) {
//			hierarchyNameTokens.add(stringTokenizer.nextToken());
//		}
//		
////		System.out.println("Tokens: " + hierarchyNameTokens);
//				
//		// Zum Wurzelelement (erster Abschnitt des Variablennamens) entsprechenden Hierarchie-Baum
//		// suchen.
//		String rootName = hierarchyNameTokens.get(0);
//		OrderedTree<HierarchyContainer> hierarchyTree = this.hierarchyTreeMap.get(rootName);
//
//		// Wenn Hierarchiebaum nicht existiert, dann anlegen.
//		if (hierarchyTree == null) {
//			HierarchyContainer hierarchyContainer = new HierarchyContainer();
//			hierarchyContainer.setHierarchyName(rootName);
//			hierarchyTree = new SimpleOrderedTree<HierarchyContainer>(hierarchyContainer);
//			this.hierarchyTreeMap.put(rootName, hierarchyTree);
//			
////			System.out.println("Neuer Hierarchiebaum. rootName: " + rootName + " hierarchyTree null? " + (hierarchyTree==null));
//		}
//		
//		
//		Node<HierarchyContainer> curNode = hierarchyTree.getRootNode();
//		for (int i=1; i<hierarchyNameTokens.size(); i++) {
//			
////			System.out.println("In der For-S");
//			
//			String hierarchyNameElement = hierarchyNameTokens.get(i);
//			boolean found = false;
//			if (!curNode.isLeaf()) {
//				List<Node<HierarchyContainer>> childNodes = curNode.getChildren();
//				for (Node<HierarchyContainer> child: childNodes) {
//					String curHierarchyElementName = child.getContent().getHierarchyName();
//					if (curHierarchyElementName.equals(hierarchyNameElement)) {
//						curNode = child;
//						found = true;
//						break;
//					}
//				}
//			}
//			if (!found || curNode.isLeaf()) {
//				HierarchyContainer hierarchyContainer = new HierarchyContainer();
//				hierarchyContainer.setHierarchyName(hierarchyNameElement);
//				curNode = curNode.createChild(hierarchyContainer);
//			}
//		}
//		curNode.getContent().setValueList(valueList);
//		
//		// debug
////		HierarchyContainer hierarchyContainer = curNode.getContent();
////		System.out.println("Inhalt: " + hierarchyContainer.getValueList().toString());
//	}
	
	/**
	 * Zerlegt den Variablenwert in Listenelemente.
	 * 
	 * @param value
	 * @return
	 */
	private List<String> getSingleLineListElements(String value) {

		if (value.startsWith("\"") && value.endsWith("\"")) {
			value = value.substring(1, value.length() - 1);
			return List.of(value);
		}
		
		String[] splitStringArray = value.split(Character.toString(Parameters.getListDelimiter()));
		ArrayList<String> splitList = new ArrayList<>();
		for (String splitElement : splitStringArray) {
			splitList.add(splitElement.trim());
		}
		return splitList;
	}


}
