package ru.ifmo.findmyfriend.server

import grails.converters.JSON

class GetController {

    def coordinats() { 
    	def currentTime = TimeUtils.getCurrentTimeGmt()
		render(contentType: "application/json") {
			result = array {
				for (rid in request.JSON.ids) {
					def u = User.findByUserId(rid)
					if (u != null) {
						user {
							id = u.userId
							if (u.timeEnd == -1 || currentTime < u.timeEnd) {
								alive = true
								latitude = u.latitude
								longitude = u.longitude
							} else {
								alive = false
							}
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
