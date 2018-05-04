package toxtree.tree.cramer3;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import toxTree.exceptions.DecisionMethodException;

public class TreeSpecification {
	protected String[] categories;

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	protected String[] rules;
	protected int transitions[][];

	public int[][] getTransitions() {
		return transitions;
	}

	/**
	 * {if no go to, if yes go to, assign if no, assign if yes}
	 * 
	 * @param transitions
	 */
	public void setTransitions(int[][] transitions) {
		this.transitions = transitions;
	}

	public String[] getRules() {
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	protected int getRuleIdByClassName(ResourceBundle bundle, String classname) {
		return Integer.parseInt(bundle.getString(classname + ".id"));
	}

	protected boolean isCategory(ResourceBundle bundle, String classname) {
		try {
			return "category".equals(bundle.getString(classname + ".type"));
		} catch (Exception x) {
			return false;
		}
	}

	public TreeSpecification() throws DecisionMethodException {
		super();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getName(), Locale.ENGLISH, this.getClass()
					.getClassLoader());
			String rulesPackage = bundle.getString("rules.package");
			String n = bundle.getString("rules");
			setRules(new String[Integer.parseInt(n)]);
			setTransitions(new int[Integer.parseInt(n)][4]);

			n = bundle.getString("categories");
			setCategories(new String[Integer.parseInt(n)]);

			Enumeration<String> keys = bundle.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				if (key.endsWith(".id")) {
					String classname = key.replace(".id", "");
					String type = null;
					try {
						type = bundle.getString(classname + ".type");
					} catch (Exception x) {
					}

					if ("category".equals(type)) {
						categories[Integer.parseInt(bundle.getString(key)) - 1] = classname;
					} else {
						rules[Integer.parseInt(bundle.getString(key)) - 1] = classname;
					}
				}
			}
			// {if no go to, if yes go to, assign if no, assign if yes}
			for (int i = 0; i < rules.length; i++) {
				String classname = rules[i];

				String yesClass = bundle.getString(classname + ".yes");
				int yes_id = getRuleIdByClassName(bundle, yesClass);
				if (isCategory(bundle, yesClass)) {
					transitions[i][3] = yes_id;
				} else {
					transitions[i][1] = yes_id;
				}

				String noClass = bundle.getString(classname + ".no");
				int no_id = getRuleIdByClassName(bundle, noClass);
				if (isCategory(bundle, noClass)) {
					transitions[i][2] = no_id;
				} else {
					transitions[i][0] = no_id;
				}

			}
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
}
