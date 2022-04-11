package advanced


class Lambda {

    val lam: () -> String = { "Something" }

    val lam1: () -> Unit = { println("Something") }

    val lam2: (Boolean, String) -> Unit = { isReady, name ->
        if (isReady) {
            println(name)
        }
    }

    val lam3: (Boolean, String) -> String = { isReady, name ->
        if (isReady) {
            println(name)
            name
        } else {
            ""
        }
    }


    fun makeSomethingOnClick(lambda: () -> Unit) {
        lambda.invoke()
    }
}

fun main(){
    val lambda: () -> Unit = { println("Something") }
    Lambda().makeSomethingOnClick(lambda)
}


class Fragment(){
    private lateinit var adapter: Adapter
    private fun showPreview(){}
    private fun removeSomething(){}
    private fun clearUser(){}

    private fun prepareAdapter(){
        adapter = Adapter(
            context = context,
            items = items,
            onClickButtonAction = {
                navigator.navigate(R.id.go_to_details)
            },
            onClickCardAction = {
                showPreview()
                removeSomething()
                clearUser()
            }
        ).apply {
            onTextClick = {
                showPreview()
                ""
            }
        }
    }

    fun onViewCreated(){
        prepareAdapter()
    }
}
//---------------
class Adapter(
    context: Context,
    items: List<Item>,
    onClickButtonAction: () -> Unit,
    onClickCardAction:() -> Unit
){
    lateinit var onTextClick: () -> String

    binding.card.setOnClickListener{
        onClickButtonAction.invoke()
    }

    binding.button.onClickListener{
        onClickCardAction()
    }
}