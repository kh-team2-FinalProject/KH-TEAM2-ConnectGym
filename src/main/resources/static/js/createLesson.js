
void (function (D) {
    "use strict";

    /**
     * @type {HTMLCollectionOf.<HTMLFormElement> & {
     *   convertForm: HTMLFormElement
     * }}
     */
    const { createLessonForm } = D.forms;
    /**
     * @type {HTMLFormControlsCollection & {
     *   profilePhoto: HTMLInputElement
     * }}
     */
    const { lessonImgFile } = createLessonForm.elements;
    /** @type {HTMLImageElement} */
    const lessonPhotoImgEl = createLessonForm.querySelector(
        "img.createLessonPhoto"
    );

    lessonImgFile.addEventListener("change", function (e) {
        const file = this.files[0];
        const lessonPhotoUrl = URL.createObjectURL(file);
        console.log("lessonPhotoUrl");
        lessonPhotoImgEl.src = lessonPhotoUrl;

        setTimeout(() => URL.revokeObjectURL(lessonPhotoUrl), 1000);
    });
})(document);
