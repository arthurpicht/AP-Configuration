package de.arthurpicht.configuration;

public class Parameters {
	
	private static char commentChar = '#';
	private static char assignmentOperator = '=';
	private static char listDelimiter = ',';

	/**
	 * Liefert das Zeichen, welches Kommentaren vorausgeht.
	 * Voreinstellung: '#'.
	 * 
	 * @return
	 */
	public static char getCommentChar() {
		return commentChar;
	}
	
	/**
	 * Definiert das Zeichen, welches Kommentaren vorausgeht.
	 * 
	 * @param commentChar
	 */
	public static void setCommentChar(char commentChar) {
		Parameters.commentChar = commentChar;
	}
	
	/**
	 * Liefert den Zuweisungsoperator für Variablen/Werte-Paare.
	 * Voreinstellung: '='.
	 * 
	 * @return
	 */
	public static char getAssignmentOperator() {
		return assignmentOperator;
	}
	
	/**
	 * Definiert den Zuweisungsoperator für Variablen/Werte-Paare.
	 * 
	 * @param assignmentOperator
	 */
	public static void setAssignmentOperator(char assignmentOperator) {
		Parameters.assignmentOperator = assignmentOperator;
	}
	
	/**
	 * Liefert das Trennzeichen für einzeilig definierte Listen.
	 * Voreinstellung: ','.
	 * 
	 * @return
	 */
	public static char getListDelimiter() {
		return listDelimiter;
	}
	
	/**
	 * Definiert das Trennzeichen für einzeilig definierte Listen.
	 * 
	 * @param listDelimiter
	 */
	public static void setListDelimiter(char listDelimiter) {
		Parameters.listDelimiter = listDelimiter;
	}
	
}
