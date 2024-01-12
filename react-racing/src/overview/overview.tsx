import * as React from "react"
import {Animated} from "react-animated-css";

export class Overview extends React.Component {

  render() {
    return (
      <div className="container">
        <div className="row justify-content-center">
          <Animated animationIn="bounceInLeft" animationOut="fadeOut" isVisible={true}>
            <h1>C.A.G Maze Runner</h1>
            <div className="text-justify">
              <p>
                In this game you will try to get through a modified, classic Brio labyrinth as fast as possible by using
                an Xbox controller.
                The time will start to run as soon as you advance through the first passage. Thereafter you will receive
                a split time when you pass the 2nd passage.
                The third passage is the finishing line, after passing it you will see your finishing time.
              </p>
              <p>
                To participate in the competition you need to register a user, log in and finally register yourself to
                the racing queue. When it is your turn to race, you will see a notification on the top of the page.
              </p>
              This game is built using technologies we have been learning at our techdays through the last year.
              For more information, visit us at the booth.
              You can find more information about us here: <a href=""
                                                              onClick={this.openCagWebpage}>https://www.cag.se/c-a-g-contactor/</a>
            </div>
          </Animated>
        </div>
      </div>
    )
  }

  openCagWebpage = () => {
    window.open("https://www.cag.se/c-a-g-contactor/", "_blank");
  }
}

