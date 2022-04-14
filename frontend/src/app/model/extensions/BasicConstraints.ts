import Extension from "./Extension";

export default interface BasicConstraints extends Extension {
  subjectIsCa: boolean,
}
