package ru.ratushin.spring.DAO;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ratushin.spring.Model.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class PersonDAO {
    private static int COUNT_PEOPLE;
    JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Person> index(){
       return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }
    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM person WHERE email = ?", new Object[]{email},
                new PersonMapper()).stream().findAny();
    }
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id =?", new Object[]{id} , new PersonMapper())
                .stream().findAny().orElse(null);
    }
    public void create(Person person){
       jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES( ?,  ?,  ?, ?)" ,
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }
    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name =?, age =?, email =?, address=? WHERE id =?",
                 person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }
    public void remove(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
    public void testMultipleUpdate(){
        List<Person> people = create1000person();
        long before = System.currentTimeMillis();
        for (Person person: people){
            jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }
    public void testBatchUpdate(){
        List<Person> people = create1000person();
        long before = System.currentTimeMillis();
        for (Person person : people){
            jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setInt(1, people.get(i).getId());
                    preparedStatement.setString(2, people.get(i).getName());
                    preparedStatement.setInt(3,people.get(i).getAge());
                    preparedStatement.setString(4, people.get(i).getEmail());
                }

                @Override
                public int getBatchSize() {
                    return people.size();
                }
            });
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public List<Person> create1000person(){
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            people.add(new Person(i, "Username" + i, 23, "user" + i + "@mail.ru", "Some address"));
        }
        return people;
    }
    public void removeAll(){
        jdbcTemplate.update("DELETE FROM person");
    }
}
