package hu.nemaberci.budgetneptun.service;

import hu.nemaberci.budgetneptun.dto.ExamDTO;
import hu.nemaberci.budgetneptun.dto.ExamResultDTO;
import hu.nemaberci.budgetneptun.entity.ExamEntity;
import hu.nemaberci.budgetneptun.entity.ExamResultEntity;
import hu.nemaberci.budgetneptun.repository.ExamRepository;
import hu.nemaberci.budgetneptun.repository.ExamResultRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserService userService;
    private final ExamResultRepository examResultRepository;

    public ExamService(
            ExamRepository examRepository,
            UserService userService,
            ExamResultRepository examResultRepository
    ) {
        this.examRepository = examRepository;
        this.userService = userService;
        this.examResultRepository = examResultRepository;
    }

    public ExamDTO create(
            String description,
            LocalDateTime time,
            Integer capacity
    ) {
        return ExamDTO.fromEntity(
                examRepository.save(
                        new ExamEntity()
                                .setDescription(description)
                                .setCapacity(capacity)
                                .setTime(time)
                                .setAttendants(new ArrayList<>())
                )
        );
    }

    public ExamDTO update(
            Long id,
            String description,
            LocalDateTime time,
            Integer capacity
    ) {

        final var exam = examRepository.getById(id);

        // We cannot put an exam in the past
        if (time.isBefore(LocalDateTime.now()) && exam.getTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The exam cannot happen in the past");
        }

        if (exam.getAttendants().size() > capacity) {
            throw new IllegalArgumentException(
                    "Capacity cannot be lower than current attendant count");
        }

        exam.setTime(time);
        exam.setCapacity(capacity);
        exam.setDescription(description);

        return ExamDTO.fromEntity(examRepository.save(exam));

    }

    public List<ExamDTO> getAll() {
        return examRepository.findAll().stream()
                .map(ExamDTO::fromEntity).collect(Collectors.toList());
    }

    public ExamDTO getById(Long id) {
        return ExamDTO.fromEntity(examRepository.getById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public ExamDTO applyToExam(
            Long examId,
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        final var exam = examRepository.getById(examId);

        if (exam.getAttendants().size() + 1 > exam.getCapacity()) {
            throw new IllegalArgumentException("Exam full");
        }

        if (exam.getAttendants().contains(student)) {
            throw new IllegalArgumentException("Student is already in the exam");
        }

        if (LocalDateTime.now().plusHours(1).isAfter(exam.getTime())) {
            throw new IllegalArgumentException("Too late for exam application");
        }

        exam.getAttendants()
                .add(student);

        return ExamDTO.fromEntity(examRepository.save(exam));

    }

    public ExamDTO removeFromExam(
            Long examId,
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        final var exam = examRepository.getById(examId);

        if (!exam.getAttendants().contains(student)) {
            throw new IllegalArgumentException("Student is not in the exam");
        }

        if (LocalDateTime.now().plusHours(1).isAfter(exam.getTime())) {
            throw new IllegalArgumentException("Too to leave exam");
        }

        exam.getAttendants()
                .remove(student);

        return ExamDTO.fromEntity(examRepository.save(exam));
    }

    public ExamResultDTO evaluateExam(
            Long examId,
            Long studentId,
            Long teacherId,
            String comment,
            Integer points
    ) {
        final var student = userService.getStudent(studentId);
        final var exam = examRepository.getById(examId);
        final var teacher = userService.getTeacher(teacherId);

        return ExamResultDTO.fromEntity(
                examResultRepository.save(
                        new ExamResultEntity()
                                .setExam(exam)
                                .setComment(comment)
                                .setPoints(points)
                                .setStudent(student)
                                .setTeacher(teacher)
                )
        );

    }

    public ExamResultDTO updateEvaluation(
            Long id,
            Long examId,
            Long studentId,
            Long teacherId,
            String comment,
            Integer points
    ) {

        final var student = userService.getStudent(studentId);
        final var exam = examRepository.getById(examId);
        final var teacher = userService.getTeacher(teacherId);
        final var examResult = examResultRepository.getById(id);

        return ExamResultDTO.fromEntity(
                examResultRepository.save(
                        examResult
                                .setExam(exam)
                                .setComment(comment)
                                .setPoints(points)
                                .setStudent(student)
                                .setTeacher(teacher)
                )
        );

    }

    public void deleteEvaluation(Long id) {
        examResultRepository.deleteById(id);
    }

    public void delete(Long id) {
        examRepository.deleteById(id);
    }

    public List<ExamResultDTO> resultsOfStudent(
            Long studentId
    ) {
        final var student = userService.getStudent(studentId);
        return examResultRepository.findAllByStudent(student)
                .stream().map(ExamResultDTO::fromEntity).collect(Collectors.toList());
    }

}
