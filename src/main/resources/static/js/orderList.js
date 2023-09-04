void (function (D) {
    "use strict";

    /**
     * @type {{
     *   orderFilterForm: HTMLFormElement
     * }}
     */
    const { orderFilterForm } = D.forms;

    /**
     * @type {{
     *   status: HTMLSelectElement
     *   startDate: HTMLInputElement
     *   endDate: HTMLInputElement
     *   q: HTMLInputElement
     * }}
     */
    const { status, startDate, endDate, q } = orderFilterForm.elements;

    let endDateValue = null;
    /**
     * @this {HTMLInputElement}
     * @param {Event} e
     */
    const dateChangeEvent = function (e) {
        startDate.max = endDate.value;

        if (
            endDate.valueAsDate &&
            startDate.valueAsDate > endDate.valueAsDate
        ) {
            endDate.valueAsDate = startDate.valueAsDate;
            return;
        }

        endDateValue = endDate.valueAsDate;
    };

    startDate.addEventListener("change", dateChangeEvent);
    endDate.addEventListener("change", dateChangeEvent);

    orderFilterForm.addEventListener("submit", function (e) {
        e.preventDefault();
        console.log(e);

        if (startDate.valueAsDate && !endDate.valueAsDate) {
            endDate.valueAsDate = new Date();
        }

        this.submit();
    });
})(document);

function movePage(page) {
    "use strict";

    if (page >= 1) {
        const params = new URLSearchParams(location.search);
        params.set("page", page);
        location.href = location.pathname + "?" + params;
    }
}
