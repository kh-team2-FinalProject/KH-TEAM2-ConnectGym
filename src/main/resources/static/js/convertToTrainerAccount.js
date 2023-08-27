void (function (D) {
    "use strict";

    /**
     * @type {HTMLCollectionOf.<HTMLFormElement> & {
     *   convertForm: HTMLFormElement
     * }}
     */
    const { convertForm } = D.forms;
    /**
     * @type {HTMLFormControlsCollection & {
     *   profilePhoto: HTMLInputElement
     * }}
     */
    const { profilePhoto } = convertForm.elements;
    /** @type {HTMLImageElement} */
    const profilePhotoImgEl = convertForm.querySelector(
        "img.convertToTrainerAccount_photo"
    );

    profilePhoto.addEventListener("change", function (e) {
        const file = this.files[0];
        const profilePhotoUrl = URL.createObjectURL(file);
        profilePhotoImgEl.src = profilePhotoUrl;

        setTimeout(() => URL.revokeObjectURL(profilePhotoUrl), 1000);
    });
})(document);
