package cn.dbdj1201.restart2022.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: yz1201
 * @Date: 2022/4/23 16:22
 */
@RestController
@Slf4j
public class SimulateFileCopyController {

//    public
public static void main(String[] args) {
    Path path = Paths.get("/home/mfs/nfs/inter/");
    Path path1 = Paths.get("/home/mfs/MFS");
    Path path2 = Paths.get("/home/mfs/nfs/inter/test/000.txt");
    Path relativize = path.relativize(path2);
    System.out.println(relativize);
    System.out.println(path1.resolve(relativize));
}

}
