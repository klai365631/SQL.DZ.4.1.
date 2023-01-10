SELECT s.name, s.age, f.name
FROM students s
LEFT JOIN faculties f on f.id=s.faculty_id;

SELECT s.name, s.age
FROM students s
INNER JOIN avatars a on s.id=a.student_id;