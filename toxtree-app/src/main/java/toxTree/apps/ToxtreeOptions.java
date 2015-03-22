/*
Ideaconsult Ltd. (C) 2005-2015  
Contact: www.ideaconsult.net

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */
package toxTree.apps;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class ToxtreeOptions {
	public boolean noui = false;

	public boolean isNoui() {
		return noui;
	}

	public void setNoui(boolean noui) {
		this.noui = noui;
	}

	protected File input;

	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	protected File output;

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	protected String batch;

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	protected String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	protected Options createOptions() {
		Options options = new Options();
		Option batch = OptionBuilder.hasArg().withLongOpt("batch")
				.withArgName("file").withDescription("Batch file").create("b");

		Option input = OptionBuilder.hasArg().withLongOpt("input")
				.withArgName("file").withDescription("Input file").create("i");

		Option output = OptionBuilder.hasArg().withLongOpt("output")
				.withArgName("file").withDescription("Output file").create("o");

		Option method = OptionBuilder.hasArg().withLongOpt("module")
				.withArgName("class").withDescription("Class name").create("m");

		Option noui = OptionBuilder.withLongOpt("noui")
				.withDescription("Command line only, do not start UI")
				.create("n");

		Option help = OptionBuilder.withLongOpt("help")
				.withDescription("This help").create("h");

		options.addOption(batch);
		options.addOption(input);
		options.addOption(output);
		options.addOption(method);
		options.addOption(noui);
		options.addOption(help);

		return options;
	}

	protected static String getModule(CommandLine line) {
		if (line.hasOption('m'))
			return line.getOptionValue('m');
		else
			return null;
	}

	protected File getOutput(CommandLine line) throws Exception {
		if (line.hasOption('o')) {
			return new File(line.getOptionValue('o'));
		} else 
			if (noui) throw new Exception("Output file not specified!");
			return null;
	}

	protected File getInput(CommandLine line) throws Exception {
		if (line.hasOption('i')) {
			File f = new File(line.getOptionValue('i'));
			if (!f.exists() && noui)
				throw new Exception("Input file does not exists!");
			return f;
		} else
			if (noui) throw new Exception("Input file not specified!");
			else return null;
	}

	protected static String getBatch(CommandLine line) {
		if (line.hasOption('b'))
			return line.getOptionValue('b');
		else
			return null;
	}

	protected static boolean getNoUI(CommandLine line) {
		return line.hasOption('n');
	}

	public boolean parse(String[] args) throws Exception {
		final Options options = createOptions();
		CommandLineParser parser = new PosixParser();
		try {
			CommandLine line = parser.parse(options, args, false);
			noui = getNoUI(line);
			input = getInput(line);
			output = getOutput(line);
			batch = getBatch(line);
			module = getModule(line);
			
			if (line.hasOption("h")) {
				printHelp(options, null);
				return false;
			} 
			return true;
		} catch (Exception x) {
			printHelp(options, x.getMessage());
			throw x;
		} finally {

		}
	}

	protected static void printHelp(Options options, String message) {
		if (message != null)
			System.out.println(message);

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(ToxtreeOptions.class.getName(), options);
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().exit(0);
	}

}
