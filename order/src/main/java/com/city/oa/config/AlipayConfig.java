package com.city.oa.config;

import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class AlipayConfig {

    public static String app_id = "2021000117635592";

    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCl8sU5GfztqhLwLJrLN+srQAD2By/oSOOQiTk8wIJBpMSAIdYXfzYDC6vHwWjdn8C5GPVch08/79nzk/0XmxtpfOtiDH5H9zX9WD+d2vnFUpM4cVpBShLpe6MoPJ7NXCS2/CRllhBRp+o0ghMFb6FOxA64CGa2/BHh9zD6mTC+bJEnjuAG+6SKxNJIrEpOfDZLBlxBQxsdPFwsx6oTd5jdJ1Mw85RY4PzNFCdLLfZrZVsW1wzKrDIblxaLX6SIkLSDou9NzNQdBFzrWV4EGFt6vDLrUTiKKw/HRccz9Q4OleTtIs2eC5HCHRaOhSedTK8OxQRCu07j5SzlX6cYBKMXAgMBAAECggEASm/kYaGzn5PbXNpVMPTLSyIxjCpbJfGUCiBTiVm8098puWP0GaQZQTJ6AmdSN59SHnUAklmrhb174VOvgmgz2pSLLPb3qiZMlHCCULeJtCY/HWFTH9gvNYYdAI54mRtM6LufQ+YVOdt9JcJ278hL7f3psEksccYN63MrCC2w/KkTLNIUPjeBPVOlUVMaIKczoo7XYaND6DeWOicj77CU7Bp3I3NcXhoH2rxH9xfCrCUQ4wW9uxJ6O/1ivoxhBlKZymcnwURqwMe1wZjz/d2XAsF5Sr/m5iBtdglp64MMADDLQANAfLtnTFMvI9m42xrhGW4r1fmDQ3aVDzp2Z8HCEQKBgQDYic199Di5PTnjQEF+64WUlgdWKL/jSr5QEWa65j8qBpRItJojQIJ2YzxRtQx174ha6MdXUXIxamD4vLWXLsj8Ex6JFV2pd1kPbAcXu1x5XOp7h7p9mpGbkiTwvSF5wOckhXIRfqkWN4KWXGN079gCDEp9KIIAsYsmomvWppi4WQKBgQDEMMkLwNpHnPSRwf0pLYjgpzle1HN1udE3kpxH84XhQGLs02TXterpwHtEVhVLd9Vl5JeSAYc3mC2aGjnJnGKSXdIewYCk5L+h0V2QbcP7rmZri73MjX8s+BD2d9QHFiAAB7PLvKH5nWjOhv70QMuFyw5ihqhPGkHGmuBT/WPI7wKBgBzPuRfZNvLuwpUKJ6zLiaqLi85a/a0wdMISjqivmhftaC2EWj32h2VZLt1wSYgnpuzxrBRiMd+BWAXAlijXgMaJhLaYsS2rQ55ZsVfnBhbf1hM5rAaqWlVozKF7iMi6cgbZ+qN5RuY93JJFGLamcwdSu1aL3UBaXzl0wwR8hjWBAoGAC0MJp2UQop+94kgScjSs8MhooKrtXcqm2dvOoMq32rBJfXK9DSziKTzlVozieAyO8WRn1vfDfUt9AqWSv6H8fo5oK1/MNKr03SMZjdrfLylZs763zHAq5rfusVc7kpSQja/jgER5v8xrYb+aDwdRJ4L6dmloa/ID6JSnX+rhuX0CgYEAzJ0xSC5rYtd7FOmRcKsXsQocC+QozLRYiEqf9BMbY6AzXl9ov0wJE6LuK8H8nw9J563wP47KpoHiiJo9kZn0cD+Vkkxan9NBPdz2iXcym9nlQIj+bAJK1MVLFrIepFF0M33Xkvyt2s7it48UxvWiR1sMIjoPpP188v6Jv6bxjLQ=";

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApfLFORn87aoS8CyayzfrK0AA9gcv6EjjkIk5PMCCQaTEgCHWF382Awurx8Fo3Z/AuRj1XIdPP+/Z85P9F5sbaXzrYgx+R/c1/Vg/ndr5xVKTOHFaQUoS6XujKDyezVwktvwkZZYQUafqNIITBW+hTsQOuAhmtvwR4fcw+pkwvmyRJ47gBvukisTSSKxKTnw2SwZcQUMbHTxcLMeqE3eY3SdTMPOUWOD8zRQnSy32a2VbFtcMyqwyG5cWi1+kiJC0g6LvTczUHQRc61leBBhberwy61E4iisPx0XHM/UODpXk7SLNnguRwh0WjoUnnUyvDsUEQrtO4+Us5V+nGASjFwIDAQAB";

    public static String notify_url = "http://127.0.0.1:8848/web/pages/order/notify.html";

    public static String return_url = "http://127.0.0.1:8848/web/pages/order/success.html";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public static String log_path = "C:\\";

    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


