package sec.project.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.domain.Todo;

@Controller
public class TodoController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/todos/{userid}", method = RequestMethod.GET)
    public String index(@PathVariable String userid, Model model) {
        String sql = "SELECT * FROM todo WHERE userid = " + userid;
        List<Todo> todos = jdbcTemplate.query(sql, new RowMapper<Todo>(){
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Todo todo = new Todo();
                todo.setId(rs.getLong("ID"));
                todo.setContent(rs.getString("CONTENT"));
                todo.setDone(rs.getBoolean("DONE"));
                todo.setUserid(rs.getLong("USERID"));
                return todo;
            }
        });
        model.addAttribute("todos", todos);
        model.addAttribute("userid", userid);

        return "todos";
    }

    @RequestMapping(value = "/todos/{userid}/create", method = RequestMethod.GET)
    public String create(@PathVariable String userid, Model model) {
        model.addAttribute("userid", userid);
        return "todoform";
    }

    @RequestMapping(value = "/todos/{userId}/{todoId}/update", method = RequestMethod.POST)
    public String update(@PathVariable String userId, @PathVariable String todoId) {
        String query = "UPDATE todo SET done = 1 WHERE id = " + todoId + " AND userid = " + userId; 

        jdbcTemplate.execute(query);

        return "redirect:/todos/" + userId;
    }

    @RequestMapping(value = "/todos/{userid}", method = RequestMethod.POST)
    public String post(@RequestParam String content, @PathVariable String userid) {
        // Internet told me this is high performance way of doing database interactions.
        String query = "INSERT INTO todo (content, userid) VALUES ('" + content + "','" + userid + "')";

        jdbcTemplate.execute(query);

        return "redirect:/todos/" + userid;
    }
}