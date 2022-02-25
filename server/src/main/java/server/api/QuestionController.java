package server.api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private List<String> questions;

    public QuestionController() {
        questions = new ArrayList<>();
        questions.addAll(List.of("Question about fridges", "Question about dishwashers", "Question about taps"));
    }

    @GetMapping(path = { "", "/" })
    public List<String> getAllQuestions() {
        return questions;
    }

    @GetMapping("/{ind}")
    public String getQuestionByIndex(@PathVariable("ind") int ind) {
        if (ind < 0 || ind >= questions.size()) return "Index out of range";
        return questions.get(ind);
    }

    @PostMapping(path = { "", "/" })
    public String addQuestion(@RequestBody String question) {
        questions.add(question);
        return "Question added";
    }

}
