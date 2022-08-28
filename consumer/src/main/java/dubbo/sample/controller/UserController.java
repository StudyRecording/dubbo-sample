package dubbo.sample.controller;

import dubbo.sample.api.UserService;
import dubbo.sample.pojo.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference
    private UserService userService;

    @GetMapping("/info")
    public User getUserInfo() {
        return userService.getUserInfo("xxx");
    }

    /**
     * 测试服务端流
     * @param name
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/sayHallo/{name}")
    public List<String> sayHallo(@PathVariable("name") String name) throws InterruptedException {

        List<String> list = new ArrayList<>();
        userService.sayHelloServerStream(name, new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("onNext:" + data);
                list.add(data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("报错了");
            }

            @Override
            public void onCompleted() {
                System.out.println("结束");
            }
        });
        return list;
    }

    @PostMapping("/sayHallo")
    public List<String> sayHallo(@RequestBody List<String> names) {
        List<String> list = new ArrayList<>();
        StreamObserver<String> request = userService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("说了啥？" + data);
                list.add(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("结束了");
            }
        });

        names.forEach(item -> {
            request.onNext(item);
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        request.onCompleted();

        return list;
    }
}
