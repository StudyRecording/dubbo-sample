package dubbo.sample.service;

import dubbo.sample.api.UserService;
import dubbo.sample.pojo.User;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public User getUserInfo(String name) {
        User user = new User();
        user.setName("dubbo");
        user.setAge(12);
        return user;
    }

    @Override
    public void sayHelloServerStream(String name, StreamObserver<String> response)
        throws InterruptedException {
        response.onNext("Hallo, " + name);
        Thread.sleep(10 * 1000);

        response.onNext("Hallo, " + name + ", 第二次");

        response.onCompleted();
    }

    @Override
    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("服务端请求参数:" + data);
                response.onNext("Hello, " + data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("provider关闭");
                response.onCompleted();
            }
        };
    }
}
