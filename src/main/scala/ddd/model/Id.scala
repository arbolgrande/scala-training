package ddd.model

trait Identity[+T] {

  val isDefined: Boolean = true

  val isEmpty: Boolean = !isDefined

  def value: T

  override def equals(obj: Any): Boolean =
    obj match {
      case id: Identity[_] => value == id.value
      case _ => false
    }
}

trait EmptyId extends Identity[Nothing] {

  override val isDefined: Boolean = false

  override def value: Nothing = throw new NoSuchElementException

  override def equals(obj: Any): Boolean =
    obj match {
      case emptyId: EmptyId => this eq emptyId
      case _ => false
    }
}

object EmptyId extends EmptyId