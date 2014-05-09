package ru.ifmo.findmyfriend.server

class PostController {

    def coordinats() { 
    	def user = request.JSON
		def u = User.findByUserId(user.id)
		if (u == null) {
			u = new User(userId: user.id)
		}
		u.latitude = Double.parseDouble(user.latitude)
		u.longitude = Double.parseDouble(user.longitude)
		u.save()		
		render status: 200
    }

    def duration() {
    	def user = request.JSON
		def u = User.findByUserId(user.id)
		if (u == null) {
			u = new User(userId: user.id)
		}
		if (user.duration == -1) {
			u.timeEnd = -1
		} else {
			u.timeEnd = user.duration + TimeUtils.getCurrentTimeGmt()
		}
		u.save()
		render status: 200
    }
}
