package uia.auth; 

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class SecureHashTest {

    @Test
    public void testSHA1() throws Exception {
        Assert.assertEquals("35675e68f4b5af7b995d9205ad0fc43842f16450", SecureHash.sha1("guest"));
        System.out.println(SecureHash.sha1("19700101080054864249" + "35675e68f4b5af7b995d9205ad0fc43842f16450"));
    }
 
    @Test
    public void test() throws Exception {
        System.out.println(SecureHash.sha1("guest"));
        System.out.println(SecureHash.sha256("guest"));
        System.out.println(SecureHash.sha512("guest"));
        System.out.println(SecureHash.md5("guest"));
    }

    @Test
    public void testFile() throws Exception {
        String md5 = SecureHash.md5(new File("sample/file.tar.bz2"));
        System.out.println(md5);
        Assert.assertEquals("b9e0653919a3aa02a70d69f16c2c976d", md5);
    }

    @Test
    public void testFile2() throws Exception {
        System.out.println(SecureHash.md5(new File("sample/b56b4c52c00c7d6dcc81db3b341aa372.xml")));
        Assert.assertEquals("003272008533494a6263bd6460c96a5b", SecureHash.md5(new File("sample/003272008533494a6263bd6460c96a5b.xml")));
        Assert.assertEquals("3545ec7a41d5a8a7deb5f677da1f26b3", SecureHash.md5(new File("sample/3545ec7a41d5a8a7deb5f677da1f26b3.xml")));
    }
}
