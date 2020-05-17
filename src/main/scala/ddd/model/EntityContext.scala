package ddd.model

trait EntityContext

object EntityContext {
  trait IdExists extends EntityContext
  trait IdEmpty  extends EntityContext
}
