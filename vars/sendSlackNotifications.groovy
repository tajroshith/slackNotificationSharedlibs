def call(String buildStatus = 'STARTED') {
  // build status of null means successful.
  //This is the condition which we are checking weather buildStatus is SUCCESS or not.
  buildStatus =  buildStatus ?: 'SUCCESS'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def branchName = env.GIT_BRANCH ?: params.BranchName ?: env.BRANCH_NAME ?: 'N/A'  // Check both env.GIT_BRANCH and env.BRANCH_NAME
  def subject = "${buildStatus}: Job '${env.NODE_NAME} ${env.JOB_NAME} [${env.BUILD_NUMBER}]' on branch '${branchName}'"
  def summary = "${subject} (${env.BUILD_URL})"

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    colorName = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    colorName = 'GREEN'
    colorCode = '#00FF00'
  } else {
    colorName = 'RED'
    colorCode = '#FF0000'
  }

  // Send notifications
  slackSend (color: colorCode, message: summary, channel: '#buildnotifications')
}
