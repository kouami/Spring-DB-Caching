package com.example.demo_cache.dao;

import com.example.demo_cache.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {

  private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String FIND_ALL = "SELECT * FROM EMPLOYEES";

  @Cacheable(cacheNames = "employees")
  public List<Employee> findAll() {

    LOG.info("Retrieving data from emp_db Employees Table");
    List<Employee> employees =
        jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Employee.class));

    LOG.info("The Size is  :: " + employees.size());
    return employees;
  }


  @Caching(evict = {@CacheEvict(value = "employees", allEntries = true)})
  public void clearAllCaches() {
    LOG.info("Cleared all caches");
  }

  /**
   * Refreshes the cache every 10 seconds
   */
  @Scheduled(cron = "${spring.job.schedule}")
  public void refreshCaches() {
    clearAllCaches();
    findAll();
  }
}
