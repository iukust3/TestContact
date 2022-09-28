# TestContact
# contact-list-sample
Features implemented using
 ☛ mvvm 
 ☛ Jetpack Compose UI 
 ☛ Paging 3 
 ☛ Room Live Data
 ☛ LazyCoulumn
 ☛ Navagation Host
 ☛ Coroutine 
 Features: 
1. Import and parse xml of contacts and save in room database
2.Load data using pagging 3 
## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/main_image.png)
```
  val contactPagging = Pager(PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 30
    )) {
        repository.getAllContacts()
    }.flow.cachedIn(viewModelScope)
```

3. Showing all the data in LazyColumn
```
 LazyColumn(
        modifier = modifier.padding(top = 10.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(gamesList.itemCount) { index ->
            gamesList[index].let { contact ->
              
                ContactCard(
                    modifier = modifier
                        .padding(8.dp),
                    contact = contact!!,
                    onClickProduct = {

                            onClickToDetailScreen.invoke(contact)

                    }
                )
            }
        }
```
4. Delete contact functionality
5. Share contact information as json object
6. Search functionality


## what's next or how can we improve this ??? 

1. Add contact functionality but first we should use fragments to create new form to add contact
2. add pagination
3. use of Flow
4. Add unit test classes

## Screen shots
![screenshot1](https://github.com/ahsanbhatti49/contact-list-sample/blob/main/screenshot_1.jpg?raw=true "screenshot1")
![screenshot2](https://github.com/ahsanbhatti49/contact-list-sample/blob/main/screenshot_2.jpg?raw=true "screenshot2")

