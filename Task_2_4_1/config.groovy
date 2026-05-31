include 'tasks_db.groovy'

// Исправили ID на Task_2_1_1
task "Task_1_1_1", "Пирамидальная сортировка", 10, "2025-09-13", "2026-09-13"
task "Task_2_2_1", "Пиццерия", 10, "2026-03-07", "2026-03-23" // Добавила еще одну для теста

group("12345") {
    student "maslova_a", "Маслова Алина", "https://github.com/24216-Maslova-Alina/OOP"
}

controlPoint "КП1", "2026-04-30"
systemSetting "maxParallelBuilds", 4

// Исправили цели проверки на те папки, что реально существуют
target student: "maslova_a", tasks: ["Task_1_1_1", "Task_2_2_1"]