const calendarButton = document.getElementById("calendarButton");
const calendarDropdown = document.getElementById("calendarDropdown");
const calendar = document.getElementById("calendar");
const selectedDateElement = document.getElementById("selectedDate");
const yearSelect = document.getElementById("yearSelect");
const monthSelect = document.getElementById("monthSelect");
const urlParams = new URLSearchParams(window.location.search);
// 캘린더 초기화


let currentDate = "";
if (urlParams != null) {
    currentDate = new Date(parseInt(urlParams.get('year')), parseInt(urlParams.get('month')), parseInt(urlParams.get('date')));
} else {
    currentDate = new Date();
}
selectedDate = currentDate;
selectedYear = currentDate.getFullYear();
selectedMonth = currentDate.getMonth();

updateSelectedDate();
updateYearSelect();
updateMonthSelect();
updateCalendar();

// 이벤트 리스너 연결
calendarButton.addEventListener("click", () => {
    calendarDropdown.style.display = "block";
});

calendar.addEventListener("click", (event) => {
    const selectedDay = parseInt(event.target.textContent);
    if (!isNaN(selectedDay)) {
        selectedDate = new Date(selectedYear, selectedMonth, selectedDay);
        updateSelectedDate();
        calendarDropdown.style.display = "none";
    }
});

document.addEventListener("click", (event) => {
    if (calendarButton.contains(event.target)) {
        calendarDropdown.style.display = "block";
    } else {
        calendarDropdown.style.display = "none";
    }
});

yearSelect.addEventListener("change", () => {
    selectedYear = parseInt(yearSelect.value);
    updateCalendar();
});

monthSelect.addEventListener("change", () => {
    selectedMonth = parseInt(monthSelect.value);
    updateCalendar();
});

function updateSelectedDate() {
    const formattedDate = `${selectedYear}년 ${selectedMonth + 1}월 ${selectedDate.getDate()}일`;
    selectedDateElement.textContent = formattedDate;
}

function updateYearSelect() {
    const currentYear = new Date().getFullYear();
    for (let year = currentYear - 10; year <= currentYear + 10; year++) {
        const option = document.createElement("option");
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }
    yearSelect.value = selectedYear;
}

function updateMonthSelect() {
    for (let month = 0; month < 12; month++) {
        const option = document.createElement("option");
        option.value = month;
        option.textContent = `${month + 1}월`;
        monthSelect.appendChild(option);
    }
    monthSelect.value = selectedMonth;
}

function updateCalendar() {
    const daysInMonth = new Date(selectedYear, selectedMonth + 1, 0).getDate();
    const firstDay = new Date(selectedYear, selectedMonth, 1).getDay();
    let calendarHTML = "";

    for (let i = 0; i < firstDay; i++) {
        calendarHTML += "<div></div>";
    }

    for (let day = 1; day <= daysInMonth; day++) {
        calendarHTML += `<div class="calendar-day">${day}</div>`;
    }

    calendar.innerHTML = calendarHTML;
}
