/**
 * 
 */
package de.katzenpapst.amunra._internal;

/**
 * @author AJ
 */
public interface Ignore {

	default void ignore() throws Exception {
		new Runner();
	}
}
