package dubbo.sample.api;

import dubbo.sample.pojo.User;
import org.apache.dubbo.common.stream.StreamObserver;

public interface UserService {

    /**
     * 获取用户信息
     * @param name
     * @return
     */
    User getUserInfo(String name);

    /**
     * 服务端流
     * @param name
     * @param response
     */
    void sayHelloServerStream(String name, StreamObserver<String> response)
        throws InterruptedException;

    /**
     * 客户端流/双向流
     * @param response
     * @return
     */
    StreamObserver<String> sayHelloStream(StreamObserver<String> response);

}
