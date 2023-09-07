/* 모달 내 시간대별 이름 바뀜 */
// document.addEventListener("DOMContentLoaded", function () {
//     var popbtns = document.querySelectorAll(".btn1");
//     popbtns.forEach(function (button) {
//         button.addEventListener("click", function () {
//             var meal = button.closest(".Diet1").querySelector("h3").textContent;
//             openPop(meal);
//         });
//     });
// });

const dietPopupContent = document.querySelector("#dietPopup .pop-content");
const foodSearchTableBodyEl = dietPopupContent.querySelector(
    "table.foodselect_Tbl > tbody"
);
const foodSearchPaginationUlEl = dietPopupContent.querySelector(
    ".dietList_search_popup_pagination .dietList_search_popup_pagination_ul"
);
const foodTimeEl = document.querySelector(
    'form[name="foodSearchForm"] > input[name="foodTime"]'
);
const foodRegDateEl = document.querySelector(
    'form[name="foodSearchForm"] > input[name="regDate"]'
);

foodRegDateEl.valueAsDate = selectedDate;

/* 열림 */
function openPop(meal) {
    foodTimeEl.value = meal.toUpperCase();

    var mealName = "";

    switch (meal) {
        case "breakfast":
            mealName += "아침";
            break;
        case "lunch":
            mealName += "점심";
            break;
        case "dinner":
            mealName += "저녁";
            break;
        case "snack":
            mealName += "간식";
            break;
        default:
            alert("알 수 없는 이름입니다. " + meal);
            return;
    }

    mealName += " MENU";
    var modal = document.getElementById("dietPopup");
    var mealNameElement = modal.querySelector(".pop-content h1");
    mealNameElement.innerText = mealName;
    modal.style.display = "block";
}

function deleteMeal(memberFoodNo) {}

/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function () {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});

// 검색 버튼 클릭 시
// function searchDiet() {
//     var foodName = document.getElementById("popInput").value;
//     var form = document.getElementById("foodSearchForm");
//     form.action = "/fooddiary/dietlist?key=" + foodName;
//     if (foodName.trim() !== "") {
//         var modal = document.getElementById("dietPopup");
//         modal.style.display = "block";
//     }
//     form.submit();
// }

function foodSearch(search, page) {
    if (!foodSearchTableBodyEl || !foodSearchPaginationUlEl) {
        console.log("일부 element가 없습니다.");
        return;
    }

    fetch(
        "/api/dietList/findFood?" +
            new URLSearchParams({
                search,
                page,
            })
    )
        .then((r) => r.json())
        .then((v) => {
            if (!v.success) {
                alert(v.message);
                return;
            }

            foodSearchTableBodyEl.innerHTML = "";
            foodSearchPaginationUlEl.innerHTML = "";

            for (const food of v.foods) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                <td>
                    <span>${food.foodNm}</span>
                    <button type="button" onclick="addFoodToDietList(${food.foodCd})"></button>
                </td>
                <td>
                    <span>${food.foodSize}g</span>
                </td>
                <td>
                    <span>${food.choc}g</span>
                </td>
                <td>
                    <span>${food.prot}g</span>
                </td>
                <td>
                    <span>${food.fat}g</span>
                </td>
                <td>
                    <span>${food.kcal}kcal</span>
                </td>
                `;

                foodSearchTableBodyEl.append(tr);
            }

            console.log(v.pagination);

            if (v.pagination.prev) {
                const liPrev = document.createElement("li");
                liPrev.innerHTML = `
                <button type="button" onclick="foodSearch('${search}', ${
                    v.pagination.firstPage - 1
                })">이전</button>
                `;
                foodSearchPaginationUlEl.append(liPrev);
            }

            for (
                let i = v.pagination.firstPage;
                i <= v.pagination.endPage;
                i++
            ) {
                console.log(i === v.pagination.currentPage);
                const li = document.createElement("li");
                li.innerHTML = `
                <button type="button" onclick="foodSearch('${search}', ${i})">${i}</button>
                `;

                i === v.pagination.currentPage &&
                    li.classList.add(
                        "dietList_search_popup_pagination_li_active"
                    );

                foodSearchPaginationUlEl.append(li);
            }

            if (v.pagination.next) {
                const liNext = document.createElement("li");
                liNext.innerHTML = `
                <button type="button" onclick="foodSearch('${search}', ${
                    v.pagination.endPage + 1
                })">다음</button>
                `;
                foodSearchPaginationUlEl.append(liNext);
            }

            dietPopupContent.scrollTo({ top: 0, behavior: "smooth" });
        });
}

document.forms.foodSearchForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const { foodTime, key } = this.elements;

    if (!foodTime || !key) {
        console.log("일부 element가 없습니다.");
        return;
    }

    foodSearch(key.value, 1);
});

function addFoodToDietList(foodNo) {
    if (!foodNo) {
        alert("필수 파라미터가 누락되었습니다.");
        return;
    }

    fetch("/api/dietList/insertFood", {
        method: "POST",
        body: new URLSearchParams({
            selectedKey: foodNo,
            foodTime: foodTimeEl.value,
            date: foodRegDateEl.value,
        }),
    })
        .then((r) => r.json())
        .then((v) => {
            if (v.success) {
                alert("선택한 음식이 추가되었습니다.");
                location.reload();
            } else {
                alert(v.message);
            }
        });
}

/* - 버튼 활성화 */

/*
function toggleButtons(foodName) {
    if (foodName !== "") {
        var tr = document.querySelector('tr[th:each="food: ${foods}"]');
        var btn1 = tr.querySelector('.btn1');
        var btn2 = tr.querySelector('.btn2');
        btn1.style.display = 'none';
        btn2.style.display = 'inline-block';
    }else {
        btn1.style.display = 'inline-block';
        btn2.style.display = 'none';
    }
}
*/
