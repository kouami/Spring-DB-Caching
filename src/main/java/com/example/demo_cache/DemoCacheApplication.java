package com.example.demo_cache;

import com.example.demo_cache.dao.EmployeeDao;
import com.example.demo_cache.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DemoCacheApplication implements CommandLineRunner {

    @Autowired
    private EmployeeDao employeeDao;

    public static void main(String[] args) {
        SpringApplication.run(DemoCacheApplication.class, args);
    }

    @Override
    public void run(String... args) {

        long start = System.currentTimeMillis();
        //Employee emp = employeeDao.findOne("Cedrick");
        //System.out.println("One Employee :: " + emp.getLastName() + " in " + (System.currentTimeMillis() - start) + " ms" );


        start = System.currentTimeMillis();
        employeeDao.findAll();
        System.out.println("From DB time :: " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        employeeDao.findAll();
        System.out.println("From Cache time :: " + (System.currentTimeMillis() - start) + "ms");

        // Pause the main thread in order

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        employeeDao.refreshCaches();
    }
}
