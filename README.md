# ShoppingCart

Shopping cart is Kotlin android library that provide you cart managment feature. This library use coroutine to make cart management fast.

### Installation

``` implementation 'de.starkling.shoppingcart:shoppingcart:1.0.0' ```

### Usage

First you need to make your existing model class a Saleable type by implementing the `Saleable` Interface. We have Product in our sample app.

```
data class Product(var id:String, var name:String ... ) : Saleable 
```
and override the methods that Saleable provides you.

```

 override var itemQuantity: Int
        get()
      
    override fun getId(): String {
       return id // This will be the id of your product class
    }

    override fun getName(): String {
       return name // name of your class
    }

    override fun getPrice(): Float {
       return price // price of your product
    }

    override fun getQuantity(): Int {
        return itemQuantity // simply return override field
    }

    override fun getTotal(): Float {
        return itemQuantity * price // calculate total of cart item
    }

```


Then you need to access the CartManager instance by following line

```
val cartManager =  CartManager.getInstance(applicationContext)
```
Add Product into Cart by following line

```val product = Product("1","Egg",15)
  product.itemQuantity = 1 // product quantity need to be hold in cart 
  
  CoroutineScope(Dispatchers.Main).launch {
            cart.updateItem(item)
  }
  
```
You can get the total items count and price by following line via LiveData Observer

```
 cart.subscribeCartTotal().observe(this, Observer {
      totalTextView.text = "Checkout (${it.totalItems})  Total Amount: ${it.totalAmount}.Rs"
 })


```

We also provide function that take your existing products or items list come from api or an other source and map with existing cart item quantity.

```
 CoroutineScope(Dispatchers.Main).launch {
       val products = cart.mapWithCart(products)
  }
        
```

Also you can remove item from cart use following method
```
cart.removeItem(product)

```

You can get the list of cart items from CartManager to show your cart items in recyclerView or for later use by following method

```
cart.getCartItems()
```

For more help and detail please checkout sample project. 

License
----

Apache License


Copyright 2020 Swenggco Software.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.





