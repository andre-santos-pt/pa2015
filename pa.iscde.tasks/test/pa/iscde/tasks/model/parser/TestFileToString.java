package pa.iscde.tasks.model.parser;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import org.junit.Assert;

public class TestFileToString {

	@Test
	public void parseAnEmptyFileReturnsEmptyString() throws IOException{
		Assert.assertEquals(new FileToString(new File("test/testFiles/empty.txt")).parse(), "");
	}
	
	@Test
	public void contentAtString() throws IOException{
		Assert.assertEquals(new FileToString(new File("test/testFiles/hello.txt")).parse(), "Hello\n");
	}
	
	@Test
	public void contentBreakLine() throws IOException{
		Assert.assertEquals(new FileToString(new File("test/testFiles/blabla.txt")).parse(), "bla\nbla\n");
	}
	
}
