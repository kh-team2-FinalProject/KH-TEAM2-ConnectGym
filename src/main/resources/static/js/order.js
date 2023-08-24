void (function (D) {
    "use strict";

    /**
     * @type {HTMLCollectionOf<HTMLFormElement> & {
     *   paymentForm: HTMLFormElement
     * }}
     */
    const { paymentForm } = D.forms;
    /**
     * @type {HTMLFormControlsCollection & {
     *   orderMethod: HTMLInputElement
     *   agreeAll: HTMLInputElement
     *   agree1: HTMLInputElement
     *   agree2: HTMLInputElement
     *   pgShopId: HTMLInputElement
     *   franchiseId: HTMLInputElement
     *   orderNo: HTMLInputElement
     *   userName: HTMLInputElement
     *   userTelNo: HTMLInputElement
     *   price: HTMLInputElement
     * }}
     */
    const {
        agree1,
        agree2,
        agreeAll,
        orderMethod,
        pgShopId,
        franchiseId,
        orderNo,
        userName,
        userTelNo,
        price,
    } = paymentForm.elements;

    /**
     * @this {HTMLInputElement}
     * @param {Event} e
     */
    const agreementButtonClickEvent = (e) => {
        if (e.target === agreeAll) {
            agree1.checked = agree2.checked = agreeAll.checked;
        } else if (e.target === agree1 || e.target === agree2) {
            agreeAll.checked = agree1.checked && agree2.checked;
        }
    };

    paymentForm.addEventListener("submit", function (e) {
        e.preventDefault();

        if (!agree1.checked || !agree2.checked || !agreeAll.checked) {
            alert("모든 항목에 동의해 주시기 바랍니다.");
            return;
        }

        switch (orderMethod.value) {
            case "card":
                break;
            case "phone":
                break;
            default:
                console.log("결제 수단을 선택해 주시기 바랍니다.");
                break;
        }

        IMP.init(franchiseId.value);

        IMP.request_pay(
            {
                pg: pgShopId.value,
                pay_method: orderMethod.value,
                merchant_uid: orderNo.value,
                name: "강의",
                amount: +price.value,
                buyer_name: userName.value,
                buyer_tel: userTelNo.value,
                // 모바일에서 결제 시 다음 URL로 리다이렉트
                m_redirect_url: location.origin + "/order/process",
            },
            function (res) {
                // PC에서 결제 시 콜백 실행
                console.log("결과", res);

                if (res.success) {
                    const body = new URLSearchParams({
                        imp_uid: res.imp_uid,
                        merchant_uid: res.merchant_uid,
                    });

                    res.error_msg && body.set("error_msg", res.error_msg);
                    res.error_code && body.set("error_code", res.error_code);

                    fetch("/api/order/process", {
                        method: "POST",
                        body,
                    })
                        .then((res) => res.json())
                        .then((v) => {
                            if (v.success) {
                                location.href = v.url;
                            }
                        });
                } else {
                    alert(res.error_msg);
                }
            }
        );
    });

    agree1.addEventListener("change", agreementButtonClickEvent);
    agree2.addEventListener("change", agreementButtonClickEvent);
    agreeAll.addEventListener("change", agreementButtonClickEvent);
})(document);
