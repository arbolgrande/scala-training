package ddd.model

trait Entity[ID <: Identity[_], CTX <: EntityContext] {

  val value: ID

  def id(implicit ev: CTX =:= EntityContext.IdExists) =
    this.value.value
  
}
