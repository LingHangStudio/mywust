package cn.wustlinghang.mywust.util;


import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import static java.lang.Integer.parseInt;

/**
 * <h>密码加密工具类</h>
 *
 * <p>使用统一登陆的加密方法对密码进行加密的工具类</p>
 * <p>研究发现统一认证登录对密码的加密方式只是简单的RSA NoPadding加密(虽然但是，貌似私钥也放在前端了...)</p>
 * <p>加密过程大概如下：</p>
 * <p>- 通过modulus模数和exponent指数生成rsa公钥</p>
 * <p>- 将明文反转后用上面生成的公钥进行RSA加密，明文填充使用NoPadding填充（0填充）</p>
 * <p>- 将生成的字节数组转换成16进制字符串，得到加密后的数据</p>
 * <br>
 * <p>希望不要再改登录方式了 [拜] </p>
 *
 * @author : lensfrex
 * @description : 使用统一登陆的加密方法对密码进行加密的工具类
 * @create : 2022-09-18 14:42:06
 * @edit : 2022-10-14 14:42:06
 */
public class PasswordEncoder {
    private static final String MODULUS = "b5eeb166e069920e80bebd1fea4829d3d1f3216f2aabe79b6c47a3c18dcee5fd22c2e7ac519cab59198ece036dcf289ea8201e2a0b9ded307f8fb704136eaeb670286f5ad44e691005ba9ea5af04ada5367cd724b5a26fdb5120cc95b6431604bd219c6b7d83a6f8f24b43918ea988a76f93c333aa5a20991493d4eb1117e7b1";
    private static final String EXPONENT = "10001";

    private static final RSAPublicKey PUBLIC_KEY = PasswordEncoder.generatePublicKey();

    private static Cipher cipher;

    // 单例加密/解密器
    static {
        try {
            cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            // 会报错的地方都是因为算法不正确，这里都是固定的值，出错了就是程序写错了，在测试的时候就应该发现的问题
            // 对于调用者不必处理这种异常，但是开发测试的时候应该要打日志来发现问题
            System.err.println("加密出错");
            e.printStackTrace();
        }
    }

    /**
     * 按照统一登陆的加密方法，加密密码
     *
     * @param password 密码明文
     * @return 加密后的密码(小写)
     */
    public static String encodePassword(String password) {
        try {
            // 反转明文
            byte[] reversedTextData = new StringBuffer(password)
                    .reverse()
                    .toString()
                    .getBytes(StandardCharsets.UTF_8);

            // 完成加密
            return Hex.encodeHexString(cipher.doFinal(reversedTextData));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            // 会报错的地方都是因为算法不正确，这里都是固定的值，出错了就是程序写错了，在测试的时候就应该发现的问题
            // 对于调用者不必处理这种异常，但是开发测试的时候应该要打日志来发现问题
            System.err.println("加密出错");
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 使用modulus模数和exponent指数生成rsa公钥
     *
     * @return 公钥
     */
    private static RSAPublicKey generatePublicKey() {
        try {
            BigInteger mod = new BigInteger(PasswordEncoder.MODULUS, 16);
            BigInteger exp = new BigInteger(PasswordEncoder.EXPONENT, 16);

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod, exp);

            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // 会报错的地方都是因为算法不正确，这里都是固定的值，出错了就是程序写错了，在测试的时候就应该发现的问题
            // 对于调用者不必处理这种异常，但是开发测试的时候应该要打日志来发现问题
            System.err.println("生成公钥出错");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <p>使用旧版登录页的方法加密密码（bkjx系统直接登录）</p>
     * <p>是直接照搬官网js代码的，只是经过了一点小修改，没有具体研究是什么加密方式（大概率可能是自创的），性能可能会有点差</p>
     *
     * @param dataString 加密前置字符串
     * @param password   密码明文
     * @return 加密后的密文
     */
    public static String legacyPassword(String username, String password, String dataString) {
        String[] parts = dataString.split("#");
        StringBuilder scode = new StringBuilder(parts[0]);
        String sxh = parts[1];

        String code = username + "%%%" + password;

        StringBuilder encoded = new StringBuilder();
        for (int i = 0, codeLength = code.length(); i < codeLength; i++) {
            if (i < 20) {
                int index = sxh.charAt(i) - 48;
                encoded.append(code.charAt(i)).append(scode, 0, index);
                scode.delete(0, index);
            } else {
                encoded.append(code, i, codeLength);
                i = codeLength;
            }
        }

        return encoded.toString();
    }
}
