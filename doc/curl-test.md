### Testing REST with curl ###

##### get User(Admin) ID=100001
`curl -s http://localhost:8080/topjava_war_exploded/rest/admin/users/100001`

##### get All Users 
`curl -s http://localhost:8080/topjava_war_exploded/rest/admin/users`

##### get Meal ID=100005
`curl -s http://localhost:8080/topjava_war_exploded/rest/profile/meals/100005`

##### get All Meals
`curl -s http://localhost:8080/topjava_war_exploded/rest/profile/meals`

##### filter Meals
`curl -s "http://localhost:8080/topjava_war_exploded/rest/profile/meals/between?dateFrom=2020-01-31&timeFrom=09:00:00&dateTo=2020-01-31&timeTo=14:00:00"`

##### filter Meals with NULL bounds
`curl -s "http://localhost:8080/topjava_war_exploded/rest/profile/meals/between?dateFrom=&timeFrom=&dateTo=&timeTo="`

##### get not existing Meal ID=888888
`curl -s -v http://localhost:8080/topjava_war_exploded/rest/profile/meals/888888`

##### create Meal
`curl -s -X POST -d '{"dateTime":"2022-09-25T09:00","description":"new test meal","calories":540}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_war_exploded/rest/profile/meals`

##### update Meal ID=100004
`curl -s -X PUT -d '{"dateTime":"2020-01-31T11:00", "description":"updated meal description", "calories":450}' -H 'Content-Type: application/json' http://localhost:8080/topjava_war_exploded/rest/profile/meals/100004`

##### delete Meal ID=100005
`curl -s -X DELETE http://localhost:8080/topjava_war_exploded/rest/profile/meals/100005`
