package toxtree.tree;

import java.util.Locale;
import java.util.ResourceBundle;

import toxTree.core.IDecisionRule;

public class BundleRuleResource {

	public static void retrieveStrings(IDecisionRule rule, String[] examples) {
		retrieveStrings(rule, rule.getClass().getName(), examples);
	}

	public static void retrieveStrings(IDecisionRule rule,
			String resourceBundle, String[] examples) {
		ResourceBundle labels = ResourceBundle.getBundle(resourceBundle,
				Locale.ENGLISH, rule.getClass().getClassLoader());
		try {
			rule.setID(labels.getString("id"));
		} catch (Exception x) {
			rule.setID(labels.getString("title"));
		}
		rule.setTitle(labels.getString("title"));
		rule.setExplanation(labels.getString("explanation"));
		try {
			examples[0] = labels.getString("example.no");
			if ("".equals(examples[0]))
				examples[0] = null;
			else {
				String[] s = examples[0].split(";");
				if (s.length > 0)
					examples[0] = s[0];
			}
		} catch (Exception x) {
			examples[0] = null;
		}

		try {
			examples[1] = labels.getString("example.yes");
			if ("".equals(examples[1]))
				examples[1] = null;
			else {
				String[] s = examples[1].split(";");
				if (s.length > 0)
					examples[1] = s[0];
			}
		} catch (Exception x) {
			examples[1] = null;
		}
	}
}
