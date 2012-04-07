package dk.smartcyp.app.test;

import java.net.URL;

import org.junit.Test;

import dk.smartcyp.app.SMARTCyp;

public class SMARTCypAppTest {
	
	/* @Test */
	public void test3A4file() throws Exception {
		URL url = getClass().getClassLoader().getResource("dk/smartcyp/app/test/3A4_substrates.sdf");
		System.out.println(url);
		SMARTCyp.main(new String[] {url.getFile()});
	}
	@Test
	public void test() throws Exception {
		
	}
	public static void main(String[] args) {
		try {
			URL url = SMARTCypAppTest.class.getClassLoader().getResource("dk/smartcyp/test/3A4_substrates.sdf");
			System.out.println(url);
			SMARTCyp.main(new String[] {url.getFile()});
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
