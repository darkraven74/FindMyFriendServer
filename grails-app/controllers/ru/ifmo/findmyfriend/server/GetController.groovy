package ru.ifmo.findmyfriend.server

import grails.converters.JSON

class GetController {

    def coordinats() { 
		render(contentType: "application/json") {
			result = array {
				for (rid in request.JSON.ids) {
					def u = User.findByUserId(rid)
					if (u != null && TimeUtils.getCurrentTimeGmt() < u.timeEnd) {
						user {
							id = u.userId
							latitude = u.latitude
							longitude = u.longitude
						}
					}
				}
			}
		}
    }

    def allUsers() {
    	render User.list() as JSON
    }
}
